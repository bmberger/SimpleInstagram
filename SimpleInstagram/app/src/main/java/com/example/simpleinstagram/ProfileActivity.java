package com.example.simpleinstagram;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.simpleinstagram.models.Post;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ImageView settingsView;
    ParseUser user;
    TextView username;
    ImageView profileImage;
    TextView bio;

    PostAdapter postAdapter;
    ArrayList<Post> posts;
    RecyclerView rvPosts;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user = getIntent().getParcelableExtra("user");
        username = (TextView) findViewById(R.id.tvUsername);
        profileImage = (ImageView) findViewById(R.id.ivUserPhoto);
        bio = (TextView) findViewById(R.id.tvBiography) ;

        username.setText(user.getUsername());
        if (user.getParseFile("profilepic") != null) {
            Glide.with(this).load(user.getParseFile("profilepic").getUrl()).apply(RequestOptions.circleCropTransform()).into(profileImage);
        }

        if (user.get("bio") != null) {
            bio.setText(String.valueOf(user.get("bio")));
        }

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_tab:
                        Intent homeIntent = new Intent(ProfileActivity.this, MainActivity.class);
                        startActivity(homeIntent);
                        return true;
                    case R.id.post_tab:
                        Intent activityIntent = new Intent(ProfileActivity.this, CreateActivity.class);
                        startActivity(activityIntent);
                        return true;
                    case R.id.profile_tab:
                        return true;
                    default: return true;
                }
            }
        });

        setupRecyclerView();
        setupRefresh();
        loadPosts(true);
    }

    public void setupRefresh() {
        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postAdapter.clear(); // clear old items
                loadPosts(true); // add new items
                swipeContainer.setRefreshing(false); // refresh is over
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    public void setupRecyclerView() {
        // find the Recycler View
        rvPosts = (RecyclerView) findViewById(R.id.rvUserPosts);
        posts = new ArrayList<>();
        // construct the adapter from this datasource
        postAdapter = new PostAdapter(posts);
        // set adapter
        rvPosts.setAdapter(postAdapter);
        // RecyclerView setup (layout manager, use adapter)
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvPosts.setLayoutManager(linearLayoutManager);

        rvPosts.addOnScrollListener(new EndlessRecyclerViewScrollListener((LinearLayoutManager) rvPosts.getLayoutManager()) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                loadPosts(false);
            }
        });
    }

    public void onSettingsClick(MenuItem mi) {
        Intent logOutIntent = new Intent(ProfileActivity.this, SettingsActivity.class);
        ParseUser myUser = ParseUser.getCurrentUser();
        logOutIntent.putExtra("user", myUser);
        startActivity(logOutIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_layout_profile, menu);

        // ** SET PROFILE's USERNAME HERE
        getSupportActionBar().setTitle(username.getText());
        return true;
    }

    public void loadPosts(final boolean firstTime) {
        // queries for the top posts
        final Post.Query postsQuery = new Post.Query();
        postsQuery.getTop().withUser();
        postsQuery.addDescendingOrder("createdAt");
        postsQuery.whereEqualTo("user", user);
        Date maxDate;

        if (!firstTime) {
            maxDate = posts.get(posts.size() - 1).getCreatedAt();
            postsQuery.whereLessThan("createdAt", maxDate);
        }
        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < objects.size(); i++) {
                        Post post = objects.get(i);
                        posts.add(post);
                        postAdapter.notifyItemInserted(posts.size() - 1);
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
