package com.sail.qa.controller;

import com.sail.qa.model.Question;
import com.sail.qa.model.ViewObject;
import com.sail.qa.service.QuestionService;
import com.sail.qa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: sail
 * @Date: 2018/12/22 19:40
 * @Version 1.0
 */

@Controller
public class HomeController {

    @Autowired
    private UserService userService=null;

    @Autowired
    private QuestionService questionService=null;

    @RequestMapping(path = {"/home","/"})
    public String home(Model model){
        List<Question> questionList = questionService.getLatestQuestions(0,0,10);
        for(Question q: questionList){
            System.out.println("q.getUserId(): "+q.getUserId());
        }
        List<ViewObject> vos = new ArrayList<>();
        for(Question q:questionList){
            ViewObject vo = new ViewObject();
            vo.set("question",q);
            vo.set("user",userService.selectUserById(q.getUserId()));
            System.out.println("user: "+userService.selectUserById(q.getUserId()));
            vos.add(vo);

        }
        model.addAttribute("vos",vos);
        return "index";
    }

    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(Model model, @PathVariable(value = "userId") String userId) {
        System.out.println("userId: "+userId);
        List<Question> questionList = questionService.getLatestQuestions(Integer.parseInt(userId),0,10);
        List<ViewObject> vos = new ArrayList<>();
        for(Question q:questionList){
            ViewObject vo = new ViewObject();
            vo.set("question",q);
            vo.set("user",userService.selectUserById(q.getUserId()));
            vos.add(vo);

        }
        model.addAttribute("vos",vos);
        return "index";
    }
}
