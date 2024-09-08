package com.flink.jdbc.utils;

import java.sql.*;

public class JDBCUtils  {

    private JDBCUtils() {
    }
    private static String driverClassName;
    private static String url;
    private static String user;
    private static String password;
    static {
       // 初始化变量 : 请将以下四个变量使用properties方式读取外部文件的方式写入
       driverClassName = "com.mysql.jdbc.Driver";
       url = "jdbc:mysql://localhost:3306/wifiprobe?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC&useSSL=false";
       user = "root";
       password = "123456";


       // 注册驱动
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    public static Connection getConnection(){
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void  closeAll(ResultSet resultSet,Statement statement,Connection connection){
        try{
            if( resultSet!=null ){
                resultSet.close();
            }

            if( statement!=null ){
                statement.close();
            }

            if( connection!=null ){
                connection.close();
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

}
