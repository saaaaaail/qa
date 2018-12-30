package com.sail.qa.interceptor;

import com.sail.qa.dao.LoginTicketDao;
import com.sail.qa.dao.UserDao;
import com.sail.qa.model.HostHolder;
import com.sail.qa.model.LoginTicket;
import com.sail.qa.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @Author: sail
 * @Date: 2018/12/23 14:46
 * @Version 1.0
 */

@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {

    @Autowired
    LoginTicketDao loginTicketDao = null;

    @Autowired
    UserDao userDao = null;

    @Autowired
    HostHolder hostHolder = null;

    //之前拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(" preHandle");
        if (hostHolder.getUser()==null){
            System.out.println("request.getRequestURI(): "+request.getRequestURI());
            response.sendRedirect("/reglogin?next="+request.getRequestURI());
        }
        return true;
    }


    //在controller处理请求之后返回modelandview之前
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if (modelAndView!=null){
            System.out.println(" postHandle");
            modelAndView.addObject("user",hostHolder.getUser());
        }
    }


    //流程结束后拦截
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
