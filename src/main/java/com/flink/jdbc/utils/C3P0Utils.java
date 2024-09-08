//package com.flink.jdbc.utils;
//
//import com.mchange.v2.c3p0.ComboPooledDataSource;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//public class C3P0Utils {
//
//    private  static ComboPooledDataSource dataSource = new ComboPooledDataSource();
//
//    private C3P0Utils() {
//    }
//
//    public static Connection getConnection(){
//
//
//        Connection connection = null;
//        try {
//            connection = dataSource.getConnection();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return connection;
//    }
//
//
//    @SuppressWarnings("all")
//    public static void  closeAll(ResultSet resultSet, Statement statement, Connection connection){
//        try{
//            if( resultSet!=null ){
//                resultSet.close();
//            }
//
//            if( statement!=null ){
//                statement.close();
//            }
//
//            if( connection!=null ){
//                connection.close();
//            }
//
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//}
