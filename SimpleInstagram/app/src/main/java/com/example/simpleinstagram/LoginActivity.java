package com.example.simpleinstagram;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    EditText usernameView;
    EditText passwordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameView = (EditText) findViewById(R.id.ivUsernameInput);
        passwordView = (EditText) findViewById(R.id.ivPasswordInput);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        getSupportActionBar().hide();
        return true;
    }

    public void onSignupClick(View view) {
        // Create the ParseUser
        ParseUser user = new ParseUser();

        // Set core properties of user
        user.setUsername(usernameView.getText().toString());
        user.setPassword(passwordView.getText().toString());

        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    // App signup worked! Go to timeline/home/main screen
                    launchApp();
                } else {
                    // Sign up didn't succeed.
                    Log.e(TAG, "Signup failure.");
                    e.printStackTrace();
                }
            }
        });
    }

    public void onLoginClick(View view) {
        // Create the ParseUser
        ParseUser user = new ParseUser();
        String username = usernameView.getText().toString();
        String password = passwordView.getText().toString();

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    // App login worked! Go to timeline/home/main screen
                    launchApp();
                } else {
                    // Login failed.
                    Log.e(TAG, "Login failure.");
                    e.printStackTrace();
                }
            }
        });
    }

    private void launchApp() {
        Log.d(TAG, "Login Successful");
        final Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
