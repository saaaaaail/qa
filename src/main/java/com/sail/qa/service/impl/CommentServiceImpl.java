package com.sail.qa.service.impl;

import com.sail.qa.dao.CommentDao;
import com.sail.qa.model.Comment;
import com.sail.qa.service.CommentService;
import com.sail.qa.service.SensitiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @Author: sail
 * @Date: 2018/12/28 13:01
 * @Version 1.0
 */

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentDao commentDao = null;

    @Autowired
    SensitiveServiceImpl sensitiveService = null;

    @Override
    public List<Comment> getCommentsByEntity(int entityId,int entityType){
        return commentDao.selectCommentByEntity(entityId,entityType);
    }

    @Override
    public int addComment(Comment comment){
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveService.filter(comment.getContent()));
        return commentDao.addComment(comment)>0 ? comment.getId():0;
    }

    @Override
    public int getCommentCount(int entityId,int entityType){
        return commentDao.getCommentCount(entityId,entityType);
    }
}
