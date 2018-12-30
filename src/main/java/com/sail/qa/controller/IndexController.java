package com.sail.qa.controller;

import com.sail.qa.model.Question;
import com.sail.qa.model.SexEnum;
import com.sail.qa.model.User;
import com.sail.qa.model.ViewObject;
import com.sail.qa.service.QuestionService;
import com.sail.qa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author: sail
 * @Date: 2018/12/22 19:40
 * @Version 1.0
 */

@Controller
@RequestMapping("/test")
public class IndexController {

    @Autowired
    private UserService userService=null;

    @Autowired
    private QuestionService questionService=null;

    @RequestMapping(path = {"/index"},method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request,
                        HttpServletResponse response,
                        HttpSession session) {
        List<Question> questionList = questionService.getLatestQuestions(0,0,10);
        for(Question q: questionList){
            System.out.println("q.getUserId(): "+q.getUserId());
        }
        List<ViewObject> vos = new ArrayList<>();
        for(Question question:questionList){
            System.out.println("question.getTitle(): "+question.getTitle());
            ViewObject vo = new ViewObject();
            vo.set("user",userService.selectUserById(question.getUserId()));
            System.out.println("userService.selectUserById(question.getUserId()).getUserPass(): "+userService.selectUserById(question.getUserId()).getUserPass());
            vo.set("question",question);
            vos.add(vo);
        }
        model.addAttribute("vos",vos);

        return "home";
    }


    @RequestMapping(path = "/selectUser")
    @ResponseBody
    public User selectUserById(@RequestParam(value = "id",defaultValue = "1") int id){
        return userService.selectUserById(id);
    }

    @RequestMapping("/selectAllUser")
    @ResponseBody
    public List<User> selectAllUser(){
        return userService.selectAllUser();
    }

    @RequestMapping(path = {"/insertUser"})
    @ResponseBody
    public Map<String,Object> insertUser(@RequestParam(value = "username",defaultValue = "yang") String username,
                                         String userpass,
                                         SexEnum sex,
                                         String salt,
                                         String headUrl){
        User user = new User();
        user.setSex(sex);
        user.setUserName(username);
        user.setUserPass(userpass);
        user.setSalt(salt);
        user.setHeadUrl(headUrl);

        int update = userService.insertUser(user);
        Map<String,Object> map = new HashMap<>();
        map.put("success",update==1);
        map.put("user",user);
        return map;
    }

    @RequestMapping(path = "/deleteUser/{pathid}")
    @ResponseBody
    public int deleteUser(@PathVariable("pathid") int id){
        return userService.deleteUser(id);
    }

    @RequestMapping(path = "/updatePassword")
    @ResponseBody
    public int updateUserPassword(User user){
        return userService.updatePassword(user);
    }

    @RequestMapping(path = "/admin",method = RequestMethod.GET)
    @ResponseBody
    public String admin(){

        return "admin";
    }

    @ResponseBody
    @ExceptionHandler
    public String error(Exception e){//指定统一的异常页面
        return "error"+e.getMessage();
    }
}
