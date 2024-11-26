package com.socialbase.model;

import java.sql.Timestamp;

public class Post {
    private int postId;            // Unique identifier for the post
    private int userId;            // ID of the user who created the post
    private String content;        // Content of the post
    private Timestamp createdAt;   // Timestamp of when the post was created

    // Constructor
    public Post(int postId, int userId, String content, Timestamp createdAt) {
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

}