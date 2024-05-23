package com.mirea.kt.ribo.users;

public class User {
    private String userId;
    private String username;
    private String profileImage;

    public User(String username, String profileImage) {
        this.username = username;
        this.profileImage = profileImage;
    }

    public String getUsername() {
        return username;
    }

    public String getProfileImage() {
        return profileImage;
    }
}