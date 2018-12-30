package com.sail.qa.typehandler;

import com.sail.qa.model.SexEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @Author: sail
 * @Date: 2018/12/22 19:40
 * @Version 1.0
 */

//声明JdbcType为Integer整型
@MappedJdbcTypes(JdbcType.INTEGER)
//声明JavaType为SexEnum
@MappedTypes(value = SexEnum.class)
public class SexTypeHandler extends BaseTypeHandler<SexEnum> {

    //设置非空性别参数
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, SexEnum sex, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i,sex.getId());
    }

    //通过列名读取性别
    @Override
    public SexEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
        int sex=resultSet.getInt(s);
        if(sex!=1&&sex!=0){return null;}
        return SexEnum.getEnumById(sex);
    }

    //通过下标读取性别
    @Override
    public SexEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        int sex = resultSet.getInt(i);
        if(sex!=1&&sex!=0){return null;}
        return SexEnum.getEnumById(sex);
    }

    //通过存储过程读取性别
    @Override
    public SexEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int sex = callableStatement.getInt(i);
        if(sex!=1&&sex!=0){return null;}
        return SexEnum.getEnumById(sex);
    }
}
