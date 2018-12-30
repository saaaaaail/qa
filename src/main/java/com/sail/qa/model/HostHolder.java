package com.sail.qa.model;

import org.springframework.stereotype.Component;

/**
 * @Author: sail
 * @Date: 2018/12/23 15:09
 * @Version 1.0
 */

@Component
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<>();

    public User getUser(){
        return users.get();
    }

    public void setUser(User user){
        users.set(user);
    }

    public void clear(){
        users.remove();
    }
}
