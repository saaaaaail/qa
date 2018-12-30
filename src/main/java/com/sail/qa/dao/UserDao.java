package com.sail.qa.dao;


import com.sail.qa.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author: sail
 * @Date: 2018/12/22 19:40
 * @Version 1.0
 */

@Mapper
public interface UserDao {
    String TABLE_NAME = " user ";
    String INSERT_FIELDS = " name as userName, password as userPass, sex, salt, head_url ";
    String SELECT_FIELDS = " id, "+INSERT_FIELDS;

    User selectById(@Param("id") int id);//方法名与mapper.xml文件的id一样

    @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where name = #{userName}"})
    User selectByName(@Param("userName") String userName);

    @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," "})
    List<User> selectAllUser();

    int addUser(User user);

    @Update({"update ",TABLE_NAME,"set password = #{userPass} where id = #{id}"})
    int updatePassword(User user);

    @Delete({"delete from ",TABLE_NAME," where id = #{id} "})
    int deleteById(int id);
}
