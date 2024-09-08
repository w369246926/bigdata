//package com.flink.jdbc;
//
//import com.flink.jdbc.utils.C3P0Utils;
//import com.itheima.jdbc.utils.C3P0Utils;
//import com.mchange.v2.c3p0.ComboPooledDataSource;
//import org.junit.Test;
//
//import java.sql.*;
//
//public class C3P0Test01 {
//
//    @Test
//    public void test01() throws Exception {
//
//        //1. 创建连接池的对象:  ComboPooledDataSource
//        // 主动去加载 src目录下 c3p0-config.xml 默认使用这个文件中 default-config 配置
//        ComboPooledDataSource dataSource = new ComboPooledDataSource();
//
//        //2. 从连接池中获取一个连接
//
//        Connection connection = dataSource.getConnection();
//        //3. 根据连接获取语句执行平台
//        String sql = "select * from user ";
//        PreparedStatement statement = connection.prepareStatement(sql);
//        //4. 执行SQL 获取结果
//        ResultSet resultSet = statement.executeQuery();
//
//        //5. 处理结果集
//        while (resultSet.next()) {
//
//            System.out.println("id:" + resultSet.getInt("id") + ";username" + resultSet.getString("username")
//                    + ";password:" + resultSet.getString("password"));
//
//        }
//
//        //6. 释放资源: 归还连接
//        resultSet.close();
//        statement.close();
//        connection.close(); // 归还连接  , 内部这个close已经进行加强, 变成归还连接操作了
//
//    }
//
//
//    // 测试 C3P0工具类是否可用
//
//    @Test
//    public void test02() throws Exception {
//
//
//        //1. 从C3P0的工具类 获取一个连接对象
//        Connection connection = C3P0Utils.getConnection();
//        //2. 根据连接获取语句执行平台
//        String sql = "select * from user ";
//        PreparedStatement statement = connection.prepareStatement(sql);
//        //3. 执行SQL 获取结果
//        ResultSet resultSet = statement.executeQuery();
//
//        //4. 处理结果集
//        while (resultSet.next()) {
//
//            System.out.println("id:" + resultSet.getInt("id") + ";username" + resultSet.getString("username")
//                    + ";password:" + resultSet.getString("password"));
//
//        }
//        //5. 释放资源: 归还连接
//        C3P0Utils.closeAll(resultSet, statement, connection);
//
//    }
//
//
//    // 事务的转账案例操作:
//    @Test
//    public void test03() {
//        //1.从c3p0连接池中获取一个连接
//        Connection connection = C3P0Utils.getConnection();
//        Statement statement = null;
//        try {
//
//            connection.setAutoCommit(false); // 关闭自动提交事务 (开启事务)
//
//            //2. 根据连接获取语句执行平台
//            statement = connection.createStatement();
//            //3. 执行SQL , 获取结果
//            String sql1 = "UPDATE bank  SET money = money-500 WHERE NAME = '张三' ";
//            String sql2 = "UPDATE bank  SET money = money+500 WHERE NAME = '李四' ";
//
//            int i1 = statement.executeUpdate(sql1);
//            int i2 = statement.executeUpdate(sql2);
//
//            //4. 处理结果集
//            System.out.println(i1 + "--" + i2);
//
//            // 如果没有异常 ,进行提交事务
//            connection.commit();
//
//
//        } catch (Exception e) {
//            try {
//                // 如果出现了异常 进行回滚事务
//                connection.rollback();
//            } catch (SQLException e1) {
//                e1.printStackTrace();
//            }
//            e.printStackTrace();
//        } finally {
//            // 不管是否有异常, 总会执行此内容
//            //5. 释放资源
//            C3P0Utils.closeAll(null, statement, connection);
//        }
//
//    }
//}
