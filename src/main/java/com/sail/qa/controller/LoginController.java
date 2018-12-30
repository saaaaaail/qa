package com.sail.qa.controller;

import com.sail.qa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;



/**
 * @Author: sail
 * @Date: 2018/12/22 19:40
 * @Version 1.0
 */

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService = null;

    @RequestMapping(path = "/register",method = RequestMethod.POST)
    public String Register(Model model,
                           @RequestParam("userName") String userName,
                           @RequestParam("userPass") String userPass,
                           @RequestParam(value = "next",required = false) String next,
                           @RequestParam(value = "rememberMe",defaultValue = "false") boolean rememberMe,
                           HttpServletResponse httpServletResponse){
        try{
            Map<String,Object> map = userService.register(userName,userPass);
            if (map.containsKey("ticket")){
                Cookie cookie = new Cookie("ticket",(String)map.get("ticket"));
                cookie.setPath("/");
                httpServletResponse.addCookie(cookie);
                if (!StringUtils.isEmpty(next)){
                    return "redirect:"+next;
                }
                return "redirect:/home";
            }else {
                model.addAttribute("msg",map.get("msg"));
                return "login";
            }

        }catch (Exception e){
            logger.error("注册异常 "+e.getMessage());
            return "login";
        }
    }

    @RequestMapping(path = "/reglogin",method = RequestMethod.GET)
    public String RegLogin(Model model, @RequestParam(value = "next", required = false) String next){
        model.addAttribute("next",next);
        return "login";
    }

    @RequestMapping(path = "/login",method = RequestMethod.POST)
    public String Login(Model model,
                        @RequestParam("userName") String userName,
                        @RequestParam("userPass") String userPass,
                        @RequestParam(value = "next",required = false) String next,
                        @RequestParam(value = "rememberMe",defaultValue = "false") boolean rememberMe,
                        HttpServletResponse httpServletResponse){
        try{
            Map<String,Object> map = userService.login(userName,userPass);
            if (map.containsKey("ticket")){
                Cookie cookie = new Cookie("ticket",(String)map.get("ticket"));
                cookie.setPath("/");
                httpServletResponse.addCookie(cookie);
                if (!StringUtils.isEmpty(next)){
                    return "redirect:"+next;
                }
                return "redirect:/home";
            }else {
                model.addAttribute("msg",map.get("msg"));
                return "login";
            }

        }catch (Exception e){
            logger.error("登录异常 "+e.getMessage());
            return "login";
        }
    }

    @RequestMapping(path = "/logout",method = RequestMethod.GET)
    public String Logout(@CookieValue("ticket")String ticket){
        userService.logout(ticket);
        return "redirect:/home";
    }


}
