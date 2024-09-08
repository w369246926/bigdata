package com.flink.dongao;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: WuGuanglei
 * @Date: 2022/01/14/15:21
 * @Description:
 */
public class MysqlConnectUtil {
    int i = 0;

    /*public Connection getConnetion() throws SQLException, ClassNotFoundException {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://192.168.88.128:3306/bigdata?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&allowMultiQueries=true&useSSL=true&serverTimezone=Asia/Shanghai";
        String user = "root";
        String password = "123456";

        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    public void excuteSql(Connection conn, SourceFileOBJ.DisturbInfoBean disturbInfoBean) throws SQLException, ClassNotFoundException {



            conn.setAutoCommit(false);
            String sql = "insert into traceone(deviceId,freq,band,freqTypeNo,freqTypeNoBig,disturbLevel,time,timeMili) values (?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
            stmt.setDouble(1, disturbInfoBean.getDeviceId());
            stmt.setDouble(2, disturbInfoBean.getFreq());
            stmt.setDouble(3, disturbInfoBean.getBand());
            stmt.setString(4, disturbInfoBean.getFreqTypeNo());
            stmt.setString(5, disturbInfoBean.getFreqTypeNoBig());
            stmt.setString(6, disturbInfoBean.getDisturbLevel());
            stmt.setString(7, disturbInfoBean.getTime());
            stmt.setLong(8, disturbInfoBean.getTimeMili());
            stmt.addBatch();//加入批处理，进行打包
            i++;
            if(i/100==0){//可以设置不同的大小；如50，100，500，1000等等
                stmt.executeBatch();
                conn.commit();
                stmt.clearBatch();
            }


        }
*/

    }

