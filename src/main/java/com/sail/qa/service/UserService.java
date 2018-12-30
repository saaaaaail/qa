package com.sail.qa.service;

import com.sail.qa.model.User;

import java.util.List;
import java.util.Map;

/**
 * @Author: sail
 * @Date: 2018/12/22 19:40
 * @Version 1.0
 */

public interface UserService {
    void printUser(User user);
    User selectUserById(int id);
    List<User> selectAllUser();
    int insertUser(User user);
    int deleteUser(int id);
    int updatePassword(User user);
    void logout(String ticket);

    Map<String,Object> register(String userName,String userPass);
    Map<String,Object> login(String userName,String userPass);

}
