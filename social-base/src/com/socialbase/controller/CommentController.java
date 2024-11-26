package com.socialbase.controller;

import com.socialbase.model.Comment;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentController {
    private Map<Integer, Comment> comments = new HashMap<>(); // Simulating a database with a HashMap
    private int commentIdCounter = 1; // To generate unique comment IDs

    // Add a comment to a post
    public Comment addComment(int postId, int userId, String content) {
        Comment newComment = new Comment(commentIdCounter++, postId, userId, content, new Timestamp(System.currentTimeMillis()));
        comments.put(newComment.getCommentId(), newComment);
        return newComment;
    }

    // Retrieve comments for a post
    public List<Comment> getCommentsByPostId(int postId) {
        List<Comment> postComments = new ArrayList<>();
        for (Comment comment : comments.values()) {
            if (comment.getPostId() == postId) {
                postComments.add(comment);
            }
        }
        return postComments;
    }
}