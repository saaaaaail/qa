package com.sail.qa.controller;

import com.sail.qa.model.Comment;
import com.sail.qa.model.EntityType;
import com.sail.qa.model.HostHolder;
import com.sail.qa.service.CommentService;
import com.sail.qa.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * @Author: sail
 * @Date: 2018/12/28 14:39
 * @Version 1.0
 */

@Controller
public class CommentController {

    private static Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    HostHolder hostHolder = null;

    @Autowired
    CommentService commentService = null;

    @RequestMapping(value = {"/addComment"}, method = {RequestMethod.POST})
    public String addComment(@RequestParam(value = "questionId",required = false)int questionId,
                             @RequestParam("content")String content){
        try{
            Comment comment = new Comment();
            comment.setContent(content);
            if (hostHolder.getUser()!=null){
                comment.setUserId(hostHolder.getUser().getId());
            }else{
                comment.setUserId(CommonUtil.ANONYMOUS_USERID);
                //return "redirect:/reglogin";
            }
            comment.setCreatedDate(new Date());
            comment.setEntityType(EntityType.ENTITY_QUESTION);
            comment.setEntityId(questionId);
            commentService.addComment(comment);
        }catch (Exception e){
            logger.error("添加评论失败 "+e.getMessage());
        }
        return "redirect:/question/" + questionId;
    }
}
