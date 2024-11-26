package com.socialbase.model;

import java.sql.Timestamp;

public class Reply {
    private int replyId;          // Unique identifier for the reply
    private int commentId;        // ID of the comment the reply belongs to
    private int userId;           // ID of the user who made the reply
    private String content;       // Content of the reply
    private Timestamp createdAt;  // Timestamp of when the reply was created

    // Constructor
    public Reply(int replyId, int commentId, int userId, String content, Timestamp createdAt) {
        this.replyId = replyId;
        this.commentId = commentId;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt; // Correct assignment
    }

    // Getters and Setters
    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getUserId() { // Note: Fixed typo here as well
        return userId;
    }

    public void setUserId(int userId) { // Note: Fixed typo here as well
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