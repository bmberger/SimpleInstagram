package com.example.simpleinstagram;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.Parse;
import com.parse.ParseUser;

public class SettingsActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ParseUser user;
    EditText bioInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        user = getIntent().getParcelableExtra("user");
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bioInput = (EditText) findViewById(R.id.bioInput);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_tab:
                        Intent homeIntent = new Intent(SettingsActivity.this, MainActivity.class);
                        startActivity(homeIntent);
                        return true;
                    case R.id.post_tab:
                        Intent activityIntent = new Intent(SettingsActivity.this, CreateActivity.class);
                        startActivity(activityIntent);
                        return true;
                    case R.id.profile_tab:
                        Intent profileIntent = new Intent(SettingsActivity.this, ProfileActivity.class);
                        profileIntent.putExtra("user", user);
                        startActivity(profileIntent);
                        return true;
                    default: return true;
                }
            }
        });
    }

    public void onLogoutClick(View v) {
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null

        Intent logOutIntent = new Intent(SettingsActivity.this, LoginActivity.class);
        startActivity(logOutIntent);
    }

    public void onChangeProfilePicClick(View v) {
        Intent getPicIntent = new Intent(SettingsActivity.this, ProfilePicActivity.class);
        getPicIntent.putExtra("user", user);
        startActivity(getPicIntent);
    }

    public void onChangeBiographyClick(View v) {
        if (bioInput != null) {
            user.put("bio", String.valueOf(bioInput.getText()));
            bioInput.setText("");
        } else {
            Toast.makeText(this, "Enter a biography first", Toast.LENGTH_LONG).show();
        }
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
