package com.socialbase.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.socialbase.model.Reply;

public class ReplyController {
    private Map<Integer, Reply> replies = new HashMap<>(); // Simulating a database with a HashMap
    private int replyIdCounter = 1; // To generate unique reply IDs

    // Add a reply to a comment
    public Reply addReply(int commentId, int userId, String content) {
        Reply newReply = new Reply(replyIdCounter++, commentId, userId, content, new Timestamp(System.currentTimeMillis()));
        replies.put(newReply.getReplyId(), newReply);
        return newReply;
    }

    // Count replies for a comment
    public int countRepliesByCommentId(int commentId) {
        int count = 0;
        for (Reply reply : replies.values()) {
            if (reply.getCommentId() == commentId) {
                count++;
            }
        }
        return count;
    }

    // Retrieve replies for a comment
    public List<Reply> getRepliesByCommentId(int commentId) {
        List<Reply> commentReplies = new ArrayList<>();
        for (Reply reply : replies.values()) {
            if (reply.getCommentId() == commentId) {
                commentReplies.add(reply);
            }
        }
        return commentReplies;
    }
}