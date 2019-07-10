package com.example.simpleinstagram;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.simpleinstagram.models.Post;

public class DetailsActivity extends AppCompatActivity {
    ImageView ivProfileImage;
    TextView topUsername;
    TextView bottomUsername;
    ImageView ivHeartImage;
    ImageView ivCommentImage;
    ImageView ivDmImage;
    ImageView ivArchiveImage;
    TextView bodyText;
    ImageView postImage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // perform findViewById lookups
        ivProfileImage = (ImageView) findViewById(R.id.profilePerson);
        topUsername = (TextView) findViewById(R.id.topUsername);
        bottomUsername = (TextView) findViewById(R.id.bottomUsername);
        ivHeartImage = (ImageView) findViewById(R.id.heartImage);
        ivCommentImage = (ImageView) findViewById(R.id.commentImage);
        ivDmImage = (ImageView) findViewById(R.id.dmImage);
        ivArchiveImage = (ImageView) findViewById(R.id.archiveImage);
        bodyText = (TextView) findViewById(R.id.bodyText);
        postImage = (ImageView) findViewById(R.id.postImage);

        Post post = getIntent().getParcelableExtra("post");

        topUsername.setText(post.getUser().getUsername());
        bottomUsername.setText(post.getUser().getUsername());
        bodyText.setText(post.getDescription());
        Glide.with(this).load(post.getImage().getUrl()).into(postImage);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_brand_round);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        return true;
    }
}
