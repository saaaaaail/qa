package com.sail.qa.service;

import com.sail.qa.model.Comment;

import java.util.List;

/**
 * @Author: sail
 * @Date: 2018/12/28 13:01
 * @Version 1.0
 */
public interface CommentService {
    public List<Comment> getCommentsByEntity(int entityId, int entityType);
    public int addComment(Comment comment);
    public int getCommentCount(int entityId,int entityType);
}
