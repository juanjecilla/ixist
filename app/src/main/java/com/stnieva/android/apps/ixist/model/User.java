package com.stnieva.android.apps.ixist.model;

/**
 * Created by stnieva on 30/3/15.
 */
public class User {

    private Type type;

    private boolean exist;

    private String id;

    private String username;

    private String name;

    public User(Type type, boolean exist, String id, String username, String name) {
        this.type = type;
        this.exist = exist;
        this.id = id == null ? "Not found" : id;
        this.username = username == null ? "Not found" : username;
        this.name = name == null ? "Not found" : name;
    }

    public Type getType() {
        return type;
    }

    public boolean isExist() {
        return exist;
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
}
