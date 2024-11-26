package com.socialbase.model;

import java.sql.Timestamp;

public class Comment {
    private int commentId;        // Unique identifier for the comment
    private int postId;           // ID of the post the comment belongs to
    private int userId;           // ID of the user who made the comment
    private String content;       // Content of the comment
    private Timestamp createdAt;  // Timestamp of when the comment was created

    // Constructor
    public Comment(int commentId, int postId, int userId, String content, Timestamp createdAt) {
        this.commentId = commentId;
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) { // Fixed method name
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