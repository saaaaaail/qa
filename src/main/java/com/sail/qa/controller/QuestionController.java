package com.sail.qa.controller;

import com.sail.qa.model.*;
import com.sail.qa.service.CommentService;
import com.sail.qa.service.QuestionService;
import com.sail.qa.service.impl.UserServiceImpl;
import com.sail.qa.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.View;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: sail
 * @Date: 2018/12/24 19:40
 * @Version 1.0
 */
@Controller
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    QuestionService questionService = null;

    @Autowired
    CommentService commentService = null;

    @Autowired
    UserServiceImpl userService = null;

    @Autowired
    HostHolder hostHolder = null;

    @RequestMapping(path = "/question/add",method = RequestMethod.POST)
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title,
                              @RequestParam("content") String content){
        try {
            Question question = new Question();
            question.setContent(content);
            question.setTitle(title);
            question.setCommentCount(0);
            question.setCreatedDate(new Date());
            if (hostHolder.getUser()!=null){
                question.setUserId(hostHolder.getUser().getId());
            }else {
                question.setUserId(CommonUtil.ANONYMOUS_USERID);
                return CommonUtil.getJSONString(999);
            }
            if(questionService.addQuestion(question)>0){
                return CommonUtil.getJSONString(0);
            };
        }catch (Exception e){
            logger.error("增加题目失败"+e.getMessage());
        }
        return CommonUtil.getJSONString(1,"提问失败");
    }

    @RequestMapping(path = "/question/{qid}")
    public String questionDetail(Model model,
                                 @PathVariable("qid")int qid){
        Question question = questionService.selectById(qid);
        List<Comment> comments = commentService.getCommentsByEntity(qid, EntityType.ENTITY_QUESTION);
        List<ViewObject> vos = new ArrayList<>();
        for (Comment comment:comments){
            ViewObject vo = new ViewObject();
            vo.set("comment",comment);
            vo.set("commentUser",userService.selectUserById(comment.getUserId()));
            vos.add(vo);
        }
        model.addAttribute("question",question);
        model.addAttribute("vos",vos);
        model.addAttribute("questionUser",userService.selectUserById(question.getUserId()));

        return "detail";
    }
}
