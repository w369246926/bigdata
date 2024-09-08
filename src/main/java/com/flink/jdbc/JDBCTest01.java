package com.flink.jdbc;

import com.flink.jdbc.utils.JDBCUtils;

import com.mysql.jdbc.Driver;

import org.junit.Test;

import java.sql.*;

public class JDBCTest01 {

    // 编写JDBC入门案例

    public static void main(String[] args) throws Exception {

        // 1.注册驱动.
        //DriverManager.registerDriver( new Driver());
        Class.forName("com.mysql.jdbc.Driver");
        // 2.获得连接.
        // url 格式:  协议:子协议://ip地址:端口号/数据库名称
        String url = "jdbc:mysql://localhost:3306/day01";
        Connection connection = DriverManager.
                getConnection(url, "root", "123456");
        // 3.获得执行sql语句的对象 : 通过连接获取SQL语句的执行平台
        Statement statement = connection.createStatement();
        // 4.执行sql语句，并返回结果
        String sql = "select * from category ";
        ResultSet resultSet = statement.executeQuery(sql);
        // 5.处理结果

        while(resultSet.next()){
            int cid = resultSet.getInt("cid");
            String cname = resultSet.getString("cname");
            System.out.println("cid:"+cid +"; cname:"+cname);
        }
        // 6.释放资源.
        resultSet.close();
        statement.close();
        connection.close();
    }

    // 使用原生的JDBC  实现对数据添加操作
    @Test
    public void test01() throws Exception{

        //1. 注册驱动
        Class.forName("com.mysql.jdbc.Driver");
        //2. 根据驱动管理类, 获取连接
        Connection connection = DriverManager.
                getConnection("jdbc:mysql:///day01", "root", "123456");
        //3. 根据连接创建sql语句执行平台
        Statement statement = connection.createStatement();
        //4. 执行SQL, 获取结果
        String sql = "insert into user  values(null,'赵六','abc123')";
        int i = statement.executeUpdate(sql);
        //5. 处理结果集:
        System.out.println("影响了"+i+"行");
        //6. 释放资源
        statement.close();
        connection.close();
    }


    // 测试工具类是否可用
    @Test
    public void test02() throws Exception{

        //1. 注册驱动
        //2. 根据驱动管理类, 获取连接
        Connection connection = JDBCUtils.getConnection();
        //3. 根据连接创建sql语句执行平台
        Statement statement = connection.createStatement();
        //4. 执行SQL, 获取结果
        String sql = "select * from user ";
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


    // 需求: 根据用户名和密码查询数据, 模拟登陆验证操作  此时test03 中 存在SQL注入的问题的

    @Test
    public void test03( ) throws Exception{
        login("张三","1231231 ' or 1 ='1");
    }

    public void login(String username,String password) throws SQLException {
        //1. 注册驱动
        //2. 根据驱动管理类, 获取连接
        Connection connection = JDBCUtils.getConnection();
        //3. 根据连接创建sql语句执行平台

        Statement statement = connection.createStatement();
        //4. 执行SQL, 获取结果
        String sql = "select  * from user where username = '"+username+"' and password = '"+password+"' ";
        ResultSet resultSet = statement.executeQuery(sql);
        //5. 处理结果集:
        boolean flag = resultSet.next();
        if(flag){ // 说明 的结果集中绝对有数据
            System.out.println("哥们. 恭喜你登陆成功了.....");
        }else {
            System.out.println("哥们.你的用户名或者密码输入错误....");
        }


        //6. 释放资源
        JDBCUtils.closeAll(resultSet,statement,connection);
    }

    // 使用 预编译的执行平台 来解决 SQL注入的问题
    @Test
    public void test04() throws Exception{

        loginPrepare("张三","123456");
    }

    public void loginPrepare(String username,String password) throws SQLException {
        //1. 注册驱动
        //2. 根据驱动管理类, 获取连接
        Connection connection = JDBCUtils.getConnection();
        //3. 根据连接创建sql语句执行平台

        //Statement statement = connection.createStatement();
        String sql = "select  * from user where username = ? and password = ? ";
        PreparedStatement statement = connection.prepareStatement(sql);
        //4. 执行SQL, 获取结果
        //4.1: 如果 SQL中有?  先给 ?  赋值
        statement.setString(1,username); // 起始值从 1开始
        statement.setString(2,password);
        //4.2 执行SQL
        ResultSet resultSet = statement.executeQuery();
        //5. 处理结果集:
        boolean flag = resultSet.next();
        if(flag){ // 说明 的结果集中绝对有数据
            System.out.println("哥们. 恭喜你登陆成功了.....");
        }else {
            System.out.println("哥们.你的用户名或者密码输入错误....");
        }


        //6. 释放资源
        JDBCUtils.closeAll(resultSet,statement,connection);
    }



    // 使用预编译的操作:  修改数据
    @Test
    public void test05() throws Exception{

        //1. 注册驱动
        //2. 根据驱动管理类来获取连接
        Connection connection = JDBCUtils.getConnection();

        //3. 根据连接获取SQL语句执行平台(预编译)
        String sql = "update user set username = ? , password = ? where id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        //4. 执行SQL 获取结果
        //4.1: 如果SQL中有?  需要先对? 进行赋值
        statement.setString(1,"田七");
        statement.setString(2,"123456");
        statement.setInt(3,4);

        //4.2: 执行SQL
        int i = statement.executeUpdate();
        //5. 处理结果
        System.out.println("影响了:"+i+"行");
        //6. 释放资源
        JDBCUtils.closeAll(null,statement,connection);

    }


    // 使用预编译的操作: 根据id查询数据
    @Test
    public void test06() throws Exception{

        //1. 注册驱动
        //2. 根据驱动管理类来获取连接
        Connection connection = JDBCUtils.getConnection();

        //3. 根据连接获取SQL语句执行平台(预编译)
        String sql = "select * from  user  where id = ? ";
        PreparedStatement statement = connection.prepareStatement(sql);
        //4. 执行SQL 获取结果
        //4.1: 如果有? 先对?赋值
        statement.setInt(1,3);

        //4.2: 执行SQL
        ResultSet resultSet = statement.executeQuery();

        //5. 处理结果
        boolean flag = resultSet.next();
        if (flag) {
            int id = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");

            System.out.println("id:"+id+";username:"+username +";password:"+password);
        }

        //6. 释放资源
        JDBCUtils.closeAll(resultSet,statement,connection);
    }


}
