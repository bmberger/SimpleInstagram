package com.example.simpleinstagram;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class CreateActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_tab:
                        Intent homeIntent = new Intent(CreateActivity.this, MainActivity.class);
                        startActivity(homeIntent);
                        return true;
                    case R.id.post_tab:
                        return true;
                    case R.id.profile_tab:
                        Intent profileIntent = new Intent(CreateActivity.this, ProfileActivity.class);
                        startActivity(profileIntent);
                        return true;
                    default: return true;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_brand_round);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        return true;
    }
}
