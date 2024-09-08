package com.flink.cktest;

import com.flink.dongao.MysqlConnectUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.util.Collector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Author itcast
 * Desc 演示DataStream-Source-基于本地/HDFS的文件/文件夹/压缩文件
 */
public class SourceFileOBJ {
    public static void main(String[] args) throws Exception {
        //TODO 0.env
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC);

        //TODO 1.source
        DataStream<String> ds1 = env.readTextFile("data/input/words1.txt").setParallelism(1);
        //DataStream<String> ds2 = env.readTextFile("data/input/dir");
        //DataStream<String> ds3 = env.readTextFile("data/input/wordcount.txt.gz");


        //TODO 2.transformation
        DataStream<TraceBean> words = ds1.flatMap(new FlatMapFunction<String, TraceBean>() {

            @Override
            public void flatMap(String value, Collector<TraceBean> out) throws Exception {
                try {
                    //System.out.println(value);
                    if (!value.startsWith("trace") || value.indexOf("[") < 0 || value.indexOf("]") < 0) {
                        System.out.println("非迹线数据");
                        return;
                    }
                    String[] split = value.split("\\|");
                    if (split.length != 16) {
                        System.out.println("数组长度不够");
                        return;
                    }
                    //List<Double> jsonArray = JSONArray.parseArray(split[15],Double.class);
                    TraceBean traceBean = new TraceBean();
                    traceBean.setKeywords(split[2] + "-" + split[3] + "-" + split[4]);
                    traceBean.setEventType(split[0]);
                    traceBean.setTraceInTime(split[1]);
                    traceBean.setDeviceId(split[2]);
                    traceBean.setStartFreq(split[3]);
                    traceBean.setStopFreq(split[4]);
                    traceBean.setRbw(split[5]);
                    traceBean.setRefLevel(split[6]);
                    traceBean.setAtt(split[7]);
                    traceBean.setGain(split[8]);
                    traceBean.setPointNum(split[9]);
                    traceBean.setSegmentNum(split[10]);
                    traceBean.setBtraceName(split[11]);
                    traceBean.setEthreshold(split[12]);
                    traceBean.setIndexName(split[13]);
                    traceBean.setCompoundCode(split[14]);
                    traceBean.setData(split[15]);
                    out.collect(traceBean);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        words.addSink(new ckSink());


        //TODO 3.sink
        //words.print();
        //ds2.print();
        //ds3.print();

        //TODO 4.execute
        env.execute();
    }

    private static class ckSink extends RichSinkFunction<TraceBean> {
        private static Connection conn;
        //private Connection conn;
        private PreparedStatement preparedStatement;
        private static MysqlConnectUtil mysqlc = new MysqlConnectUtil();
        int count = 0;

        @Override
        public void open(Configuration parameters) throws Exception {
            super.open(parameters);
            //加载JDBC驱动
            //  Class.forName("com.mysql.jdbc.Driver");
            //Class.forName("ru.yandex.clickhouse.ClickHouseDriver");
            //获取数据库连接
            //  conn = DriverManager.getConnection("jdbc:mysql://192.168.88.128:3306/bigdata","root","123456");
            //conn = DriverManager.getConnection("jdbc:clickhouse://192.168.88.162:8123/default");
            //String sql = "INSERT INTO `user_order_all` (`oder_id`, `user_id`, `price`, `creat_time`) VALUES (?,?,?,?);";
            //conn.prepareStatement(sql);
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
                for (int i= 0; i<1000; i++) {
                    gotomysql();
                }
                    preparedStatement.setString(1, value.getKeywords());
                    preparedStatement.setString(2, value.getEventType());
                    preparedStatement.setString(3, value.getTraceInTime());
                    preparedStatement.setString(4, value.getDeviceId());
                    preparedStatement.setString(5, value.getStartFreq());
                    preparedStatement.setString(6, value.getStopFreq());
                    preparedStatement.setString(7, value.getRbw());
                    preparedStatement.setString(8, value.getRefLevel());
                    preparedStatement.setString(9, value.getAtt());
                    preparedStatement.setString(10, value.getGain());
                    preparedStatement.setString(11, value.getPointNum());
                    preparedStatement.setString(12, value.getSegmentNum());
                    preparedStatement.setString(13, value.getBtraceName());
                    preparedStatement.setString(14, value.getEthreshold());
                    preparedStatement.setString(15, value.getIndexName());
                    preparedStatement.setString(16, value.getCompoundCode());
                    preparedStatement.setString(17, value.getData());
                    preparedStatement.addBatch();
                    count ++;
                System.out.println("这是累计"+count);
                    //preparedStatement.();
                    if (count % 100 == 0) {//可以设置不同的大小；如50，100，500，1000等等
                        System.out.println("这是进入"+count);
                        //preparedStatement.executeBatch();
                        //conn.commit();
                        //preparedStatement.clearBatch();
                        //count = 0;
                    }



                //preparedStatement.executeBatch();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        public static  void gotomysql() throws SQLException, ClassNotFoundException {
            DisturbInfoBean disturbInfoBean = new DisturbInfoBean();
            disturbInfoBean.setDeviceId(1);
            disturbInfoBean.setFreq(26.2);
            disturbInfoBean.setBand(27.7);
            disturbInfoBean.setFreqTypeNo("266262");
            disturbInfoBean.setFreqTypeNoBig("15225");
            disturbInfoBean.setDisturbLevel("qwe");
            disturbInfoBean.setTime("eer");
            disturbInfoBean.setTimeMili(2132312312312312l);

            //conn = mysqlc.getConnetion();
            //mysqlc.excuteSql(conn, (com.flink.dongao.SourceFileOBJ.DisturbInfoBean) disturbInfoBean);
        }

    }



    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TraceBean {
        private String keywords;
        private String eventType;
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
        private String data;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DisturbInfoBean {

        private Integer deviceId;
        private double freq;
        private double band;
        private String freqTypeNo;//用频类别编号（小类）
        private String freqTypeNoBig;//用频类别（大类）
        private String disturbLevel; //风险等级
        private String time;
        private Long timeMili;

    }
}
