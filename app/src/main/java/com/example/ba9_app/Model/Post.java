package com.example.ba9_app.Model;

public class Post {
    private String image;
    private String user;
    private String caption;
    private String date;

    public Post() {
        // Default constructor required for Firestore
    }

    public Post(String image, String user, String caption, String date) {
        this.image = image;
        this.user = user;
        this.caption = caption;
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
