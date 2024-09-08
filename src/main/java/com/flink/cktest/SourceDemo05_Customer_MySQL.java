package com.flink.cktest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Author itcast
 * Desc 演示DataStream-Source-自定义数据源-MySQL
 * 需求:
 */
public class SourceDemo05_Customer_MySQL {
    public static void main(String[] args) throws Exception {
        //TODO 0.env
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC);

        //TODO 1.source
        DataStream<Student> studentDS = env.addSource(new MySQLSource()).setParallelism(1);

        //TODO 2.transformation

        //TODO 3.sink
        studentDS.print();

        //TODO 4.execute
        env.execute();
    }




    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Student {
        private Integer id;
        private String traceInTime;
        private String deviceId;
        private String startFreq;
        private String stopFreq;
        private String rbw;
        private String refLevel;
        private String att;
        private String gain;
        private String pointNum;
        private String segmentNum;
        private String btraceName;
        private String ethreshold;
        private String indexName;
        private String compoundCode;
        private String occupancy;
        private String disturb;
    }
    /*
    * CREATE TABLE `trace_occupancy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `traceInTime` varchar(255) NOT NULL,
  `deviceId` varchar(255) DEFAULT NULL,
  `startFreq` varchar(255) DEFAULT NULL,
  `stopFreq` varchar(255) DEFAULT NULL,
  `rbw` varchar(255) DEFAULT NULL,
  `refLevel` varchar(255) DEFAULT NULL,
  `att` varchar(255) DEFAULT NULL,
  `gain` varchar(255) DEFAULT NULL,
  `pointNum` varchar(255) DEFAULT NULL,
  `segmentNum` varchar(255) DEFAULT NULL,
  `btraceName` varchar(255) DEFAULT NULL,
  `ethreshold` varchar(255) DEFAULT NULL,
  `indexName` varchar(255) DEFAULT NULL,
  `compoundCode` varchar(255) DEFAULT NULL,
  `occupancy` text,
  `disturb` text,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3309192 DEFAULT CHARSET=utf8*/

    public static class MySQLSource extends RichParallelSourceFunction<Student> {
        private boolean flag = true;
        private Connection conn = null;
        private PreparedStatement ps =null;
        private ResultSet rs  = null;
        //open只执行一次,适合开启资源
        @Override
        public void open(Configuration parameters) throws Exception {
            conn = DriverManager.getConnection("jdbc:mysql://39.96.139.32:5391/emcasd", "root", "123456");
            String sql = "select id,traceInTime,deviceId,startFreq,stopFreq,rbw,refLevel,att,gain,pointNum,segmentNum,btraceName,ethreshold,indexName,compoundCode,occupancy,disturb from trace_occupancy";
            ps = conn.prepareStatement(sql);
        }

        @Override
        public void run(SourceContext<Student> ctx) throws Exception {
            while (flag) {
                rs = ps.executeQuery();//执行查询
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String traceInTime = rs.getString("traceInTime");
                    String deviceId = rs.getString("deviceId");
                    String startFreq = rs.getString("startFreq");
                    String stopFreq = rs.getString("stopFreq");
                    String rbw = rs.getString("rbw");
                    String refLevel = rs.getString("refLevel");
                    String att = rs.getString("att");
                    String gain = rs.getString("gain");
                    String pointNum = rs.getString("pointNum");
                    String segmentNum = rs.getString("segmentNum");
                    String btraceName = rs.getString("btraceName");
                    String ethreshold = rs.getString("ethreshold");
                    String indexName = rs.getString("indexName");
                    String compoundCode = rs.getString("compoundCode");
                    String occupancy = rs.getString("occupancy");
                    String disturb = rs.getString("disturb");
                    ctx.collect(new Student(id,traceInTime,deviceId,startFreq,stopFreq,rbw,refLevel,att,gain,pointNum,segmentNum,btraceName,ethreshold,indexName,compoundCode,occupancy,disturb));
                }
                //Thread.sleep(5000);
            }
        }

        //接收到cancel命令时取消数据生成
        @Override
        public void cancel() {
            flag = false;
        }

        //close里面关闭资源
        @Override
        public void close() throws Exception {
            if(conn != null) conn.close();
            if(ps != null) ps.close();
            if(rs != null) rs.close();
        }
    }

}
