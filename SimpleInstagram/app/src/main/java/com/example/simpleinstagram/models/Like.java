package com.example.simpleinstagram.models;

import com.parse.ParseFile;
import com.parse.ParseUser;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("Like")
public class Like extends ParseObject {
    // encapsulate it so all logic is here
    private static final String KEY_POST = "post";
    private static final String KEY_USER = "user";

    public ParseObject getPost() { return getParseObject(KEY_POST); }

    public void setPost(ParseObject p) { put(KEY_POST, p); }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

}
