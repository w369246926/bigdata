package com.flink.jdbc;

import com.flink.jdbc.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class jdbctest {
    public static void main(String[] args) throws SQLException {
        //1. 注册驱动
        //2. 根据驱动管理类, 获取连接
        Connection connection = JDBCUtils.getConnection();
        //3. 根据连接创建sql语句执行平台
        Statement statement = connection.createStatement();
        //4. 执行SQL, 获取结果
        String sql = "select * from wifiprobe1 ";
        ResultSet resultSet = statement.executeQuery(sql);
        //5. 处理结果集:
        while(resultSet.next()){

            int id = resultSet.getInt("id");
            String username = resultSet.getString("mac");
            String password = resultSet.getString("ssid");

            System.out.println("id:"+id+";username"+username+";password:"+password);
        }

        //6. 释放资源
        JDBCUtils.closeAll(resultSet,statement,connection);
    }
}
