package com.sail.qa.dao;

import com.sail.qa.model.LoginTicket;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

/**
 * @Author: sail
 * @Date: 2018/12/22 19:40
 * @Version 1.0
 */

@Mapper
public interface LoginTicketDao {
    String TABLE_NAME = "login_ticket";
    String INSERT_FIELDS = " user_id, expired, status, ticket ";
    String SELECT_FIELDS = " id, "+INSERT_FIELDS;

    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,") values (#{userId},#{expired},#{status},#{ticket})"})
    int addTicket(LoginTicket loginTicket);

    @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where ticket = #{ticket} "})
    LoginTicket selectByTicket(String ticket);

    @Update({"update ",TABLE_NAME," set status = #{status} where ticket = #{ticket} "})
    void updateStatus(@Param("ticket") String ticket,@Param("status") int status);
}
