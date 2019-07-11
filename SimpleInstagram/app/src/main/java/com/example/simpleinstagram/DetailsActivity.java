package com.example.simpleinstagram;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.simpleinstagram.models.Post;
import com.parse.ParseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

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
    TextView relTimeAgo;

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
        relTimeAgo = (TextView) findViewById(R.id.relTimeAgo);

        Post post = getIntent().getParcelableExtra("post");

        topUsername.setText(post.getUser().getUsername());
        bottomUsername.setText(post.getUser().getUsername());
        bodyText.setText(post.getDescription());
        Glide.with(this).load(post.getImage().getUrl()).into(postImage);
        if (post.getUser().getParseFile("profilepic") != null) {
            Glide.with(this).load(post.getUser().getParseFile("profilepic").getUrl()).apply(RequestOptions.circleCropTransform()).into(ivProfileImage);
        }
        relTimeAgo.setText(getRelativeTimeAgo(post.getDate()));
    }

    // relative timestamp on each tweet
    public String getRelativeTimeAgo(String rawJsonDate) {
        String instaFormat = "EEE MMM dd hh:mm:ss zzz yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(instaFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //reformat string and turn "minutes ago" into "m" and "seconds ago" to "s"
        relativeDate = relativeDate.replaceAll(" second.* ago", "s");
        relativeDate = relativeDate.replaceAll(" minute.* ago", "m");
        relativeDate = relativeDate.replaceAll(" hour.* ago", "h");
        relativeDate = relativeDate.replaceAll(" day.* ago", "d");
        relativeDate = relativeDate.replaceAll(" week.* ago", "w");
        relativeDate = relativeDate.replaceAll(" month.* ago", "mo");

        return relativeDate;
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
