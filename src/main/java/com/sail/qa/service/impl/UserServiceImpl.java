package com.sail.qa.service.impl;

import com.sail.qa.dao.LoginTicketDao;
import com.sail.qa.dao.UserDao;
import com.sail.qa.model.LoginTicket;
import com.sail.qa.model.User;
import com.sail.qa.service.UserService;
import com.sail.qa.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @Author: sail
 * @Date: 2018/12/22 19:40
 * @Version 1.0
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao = null;

    @Autowired
    private LoginTicketDao loginTicketDao = null;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,timeout = 1)
    public User selectUserById(int id) {
        return userDao.selectById(id);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE,timeout = 1,propagation = Propagation.REQUIRES_NEW)
    public List<User> selectAllUser(){
        return userDao.selectAllUser();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,timeout = 1)
    public int insertUser(User user) {
        return userDao.addUser(user);
    }


    @Transactional(isolation = Isolation.SERIALIZABLE,timeout = 1,propagation = Propagation.REQUIRES_NEW)
    @Override
    public int deleteUser(int id){
        return userDao.deleteById(id);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,timeout = 1,propagation = Propagation.REQUIRED)
    @Override
    public int updatePassword(User user) {
        return userDao.updatePassword(user);
    }

    @Override
    public Map<String, Object> register(String userName, String userPass) {
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isEmpty(userName)){
            map.put("msg","用户名不能为空");
            return map;
        }
        if(StringUtils.isEmpty(userPass)){
            map.put("msg","密码不能为空");
            return map;
        }

        User user = userDao.selectByName(userName);
        if (user!=null){
            map.put("msg","用户名已经被注册");
            return map;
        }
        user = new User();
        user.setUserName(userName);
        user.setSalt(UUID.randomUUID().toString().substring(0,10));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));
        user.setUserPass(CommonUtil.MD5(userPass+user.getSalt()));
        userDao.addUser(user);

        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);

        return map;
    }

    @Override
    public Map<String,Object> login(String userName, String userPass) {
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isEmpty(userName)){
            map.put("msg","用户名不能为空");
            return map;
        }
        if(StringUtils.isEmpty(userPass)){
            map.put("msg","密码不能为空");
            return map;
        }

        User user = userDao.selectByName(userName);
        if(user==null){
            map.put("msg","用户名不存在");
            return map;
        }
        String password = CommonUtil.MD5(userPass+user.getSalt());
        if(!user.getUserPass().equals(password)){
            map.put("msg","密码不正确");
            return map;
        }

        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        map.put("user",user);

        return map;
    }

    public String addLoginTicket(int userId){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date now = new Date();
        System.out.println("now: "+now);
        now.setTime(3600*24*10000 + now.getTime());
        System.out.println("now: "+now);
        loginTicket.setExpired(now);
        loginTicket.setStatus(0);//
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        loginTicketDao.addTicket(loginTicket);
        return loginTicket.getTicket();
    }

    @Override
    public void logout(String ticket) {
        loginTicketDao.updateStatus(ticket,1);
    }

    @Override
    public void printUser(User user) {
        if(user==null){
            throw new RuntimeException("检查用户名是否为null");
        }
        System.out.println("id= "+user.getId());
        System.out.println("username= "+user.getUserName());
        System.out.println("userpass= "+user.getUserPass());
    }
}
