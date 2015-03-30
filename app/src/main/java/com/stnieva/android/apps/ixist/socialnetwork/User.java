package com.stnieva.android.apps.ixist.socialnetwork;

/**
 * Created by stnieva on 30/3/15.
 */
public class User {

    private String id;

    private String username;

    private String name;

    private Type type;

    public User(String id, String username, String name, Type type) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }
}
