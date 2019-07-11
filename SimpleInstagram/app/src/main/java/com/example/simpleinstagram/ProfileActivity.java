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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ImageView settingsView;
    ParseUser user;
    TextView username;
    ImageView profileImage;
    TextView bio;

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
}
