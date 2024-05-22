package com.mirea.kt.ribo.bottom_navigation.profile.friends;

public class Friend {
    private String userName;
    private int age;
    private String profileImage;

    public Friend(String userName, int age, String profileImage) {
        this.userName = userName;
        this.age = age;
        this.profileImage = profileImage;
    }

    public Friend(String userName, String profileImage) {
        this.userName = userName;
        this.profileImage = profileImage;
    }

    public Friend(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public int getAge() {
        return age;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
