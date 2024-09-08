package com.flink.dongao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
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
public class Source_MySQL {
    public static void main(String[] args) throws Exception {
        //TODO 0.env
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC);

        //TODO 1.source
        DataStream<TraceBean> studentDS = env.addSource(new MySQLSource()).setParallelism(1);

        //TODO 2.transformation

        //TODO 3.sink
        studentDS.print();
        studentDS.addSink(new ckSink());

        //TODO 4.execute
        env.execute();
    }




    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TraceBean {
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

    public static class MySQLSource extends RichParallelSourceFunction<TraceBean> {
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
        public void run(SourceContext<TraceBean> ctx) throws Exception {
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
                    ctx.collect(new TraceBean(id,traceInTime,deviceId,startFreq,stopFreq,rbw,refLevel,att,gain,pointNum,segmentNum,btraceName,ethreshold,indexName,compoundCode,occupancy,disturb));
                }
                Thread.sleep(5000);
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

    private static class ckSink extends RichSinkFunction<TraceBean> {
        private Connection conn;
        //private Connection conn;
        private PreparedStatement preparedStatement;
        //private static MysqlConnectUtil mysqlc = new MysqlConnectUtil();
        @Override
        public void open(Configuration parameters) throws Exception {
            super.open(parameters);
            //加载JDBC驱动
              Class.forName("com.mysql.jdbc.Driver");
            //Class.forName("ru.yandex.clickhouse.ClickHouseDriver");
            //获取数据库连接
              conn = DriverManager.getConnection("jdbc:mysql://192.168.88.161:3306/emcasd","root","123456");
            //conn = DriverManager.getConnection("jdbc:clickhouse://192.168.88.162:8123/default");
            String sql = "INSERT INTO `trace_occupancy` (`id`, `traceInTime`, `deviceId`, `startFreq`, `stopFreq`, `rbw`, `refLevel`, `att`, `gain`, `pointNum`, `segmentNum`, `btraceName`, `ethreshold`, `indexName`, `compoundCode`, `occupancy`, `disturb`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            preparedStatement = conn.prepareStatement(sql);
            //  conn.setAutoCommit(false);
            //  preparedStatement = conn.prepareStatement("INSERT INTO traceone(keywords,eventType,traceInTime,deviceId,startFreq,stopFreq,rbw,refLevel,att,gain,pointNum,segmentNum,btraceName,ethreshold,indexName,compoundCode,dataa) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        }

        @Override
        public void close() throws Exception {
            super.close();
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

        @Override
        public void invoke(TraceBean value, Context context) throws Exception {
            try {
                preparedStatement.setString(1, String.valueOf(value.getId()));
                preparedStatement.setString(2,  value.getDeviceId());
                preparedStatement.setString(3,  value.getTraceInTime());
                //preparedStatement.setString(4,  value.getDeviceId());
                preparedStatement.setString(4,  value.getStartFreq());
                preparedStatement.setString(5,  value.getStopFreq());
                preparedStatement.setString(6,  value.getRbw());
                preparedStatement.setString(7,  value.getRefLevel());
                preparedStatement.setString(8,  value.getAtt());
                preparedStatement.setString(9, value.getGain());
                preparedStatement.setString(10, value.getPointNum());
                preparedStatement.setString(11, value.getSegmentNum());
                preparedStatement.setString(12, value.getBtraceName());
                preparedStatement.setString(13, value.getEthreshold());
                preparedStatement.setString(14, value.getIndexName());
                preparedStatement.setString(15, value.getCompoundCode());
                preparedStatement.setString(16, value.getOccupancy());
                preparedStatement.setString(17,value.getDisturb());
                preparedStatement.executeUpdate();
                //preparedStatement.();
                //preparedStatement.executeBatch();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}
