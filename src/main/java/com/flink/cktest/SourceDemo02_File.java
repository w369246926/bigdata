package com.flink.cktest;

import com.alibaba.fastjson.JSONArray;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple14;
import org.apache.flink.api.java.tuple.Tuple17;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Properties;

/**
 * Author itcast
 * Desc 演示DataStream-Source-基于本地/HDFS的文件/文件夹/压缩文件
 */
public class SourceDemo02_File {
    public static void main(String[] args) throws Exception {
        //TODO 0.env
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC);

        //TODO 1.source
        //DataStream<String> ds1 = env.readTextFile("data/input/words.txt");
        //DataStream<String> ds2 = env.readTextFile("data/input/dir");
        //DataStream<String> ds3 = env.readTextFile("data/input/wordcount.txt.gz");
        //TODO 1.source
        //准备kafka连接参数
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", " 39.96.136.60:9092");//集群地址
        props.setProperty("group.id", "flink_kafka");//消费者组id
        FlinkKafkaConsumer<String> kafkaSource = new FlinkKafkaConsumer<String>("da_trace", new SimpleStringSchema(), props);
        DataStream<String> kafkaDS = env.addSource(kafkaSource);

        //TODO 2.transformation
        DataStream<Tuple17<String,String, String, String, String, String, String,String, String, String,String, String, String,String, String, String,String>> dataStream = kafkaDS.map(new MapFunction<String, Tuple17<String,String, String, String, String, String, String,String, String, String,String, String, String,String, String, String,String>>() {
            @Override
            public Tuple17<String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String> map(String s) throws Exception {
                try {


                    //System.out.println(s);
                    if (!s.startsWith("trace") || s.indexOf("[") < 0 || s.indexOf("]") < 0) {
                        System.out.println("非迹线数据");
                        //return null;
                    }
                    String[] split = s.split("\\|");
                    if (split.length != 16) {
                        System.out.println("数组长度不够");
                        //return null;
                    }
                    String Keywords = split[2] + "-" + split[3] + "-" + split[4];
                    //traceBean.setEventType(split[0]);
                    String EventType = split[0];
                    String TraceInTime = split[1];
                    String DeviceId = split[2];
                    String StartFreq = (split[3]);
                    String StopFreq = (split[4]);
                    String Rbw = (split[5]);
                    String RefLevel = (split[6]);
                    String Att = (split[7]);
                    String Gain = (split[8]);
                    String PointNum = (split[9]);
                    String SegmentNum = (split[10]);
                    String BtraceName = (split[11]);
                    String Ethreshold = (split[12]);
                    String IndexName = (split[13]);
                    String CompoundCode = (split[14]);
                    String data = (split[15]);
                    return new Tuple17<>(Keywords, EventType, TraceInTime, DeviceId, StartFreq, StopFreq, Rbw, RefLevel, Att, Gain, PointNum, SegmentNum, BtraceName, Ethreshold, IndexName, CompoundCode, data);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        });

        //TODO 3.sink
        dataStream.print();
        dataStream.addSink(new ckSink());
        //TODO 4.execute
        env.execute();


    }


    public static class ckSink extends RichSinkFunction<Tuple17<String,String, String, String, String, String, String,String, String, String,String, String, String,String, String, String,String>> {
        private Connection conn;
        private PreparedStatement preparedStatement;

        @Override
        public void open(Configuration parameters) throws Exception {
            super.open(parameters);
            //加载JDBC驱动
            Class.forName("ru.yandex.clickhouse.ClickHouseDriver");
            //Class.forName("com.mysql.jdbc.Driver");
            //获取数据库连接
            conn = DriverManager.getConnection("jdbc:clickhouse://192.168.88.136:8123/test","default","123456");
            //conn = DriverManager.getConnection("jdbc:mysql://192.168.88.161:3306/bigdata","root","123456");
            //String sql = "INSERT INTO `user_order_all` (`oder_id`, `user_id`, `price`, `creat_time`) VALUES (?,?,?,?);";
            //conn.prepareStatement(sql);
            preparedStatement = conn.prepareStatement("INSERT INTO trace(keywords,eventType,traceInTime,deviceId,startFreq,stopFreq,rbw,refLevel,att,gain,pointNum,segmentNum,btraceName,ethreshold,indexName,compoundCode,data) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        }

        @Override
        public void close() throws Exception {
            super.close();
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

        @Override
        public void invoke(Tuple17<String,String, String, String, String, String, String,String, String, String,String, String, String,String, String, String,String> value, Context context) throws Exception {
            try {
                String keywords = value.f0;
                String eventType = value.f1;
                String traceInTime = value.f2;
                String deviceId = value.f3;
                String startFreq = value.f4;
                String stopFreq = value.f5;
                String rbw = value.f6;
                String refLevel = value.f7;
                String att = value.f8;
                String gain = value.f9;
                String pointNum = value.f10;
                String segmentNum = value.f11;
                String btraceName = value.f12;
                String ethreshold = value.f13;
                String indexName = value.f14;
                String compoundCode = value.f15;
                String data = value.f16;

//                int oderid = value.f0;
//                int userid = value.f1;
//                float price = value.f2;
//                String creattime = value.f3;
                preparedStatement.setString(1,keywords);
                preparedStatement.setString(2,eventType);
                preparedStatement.setString(3,traceInTime);
                preparedStatement.setString(4,deviceId);
                preparedStatement.setString(5,startFreq);
                preparedStatement.setString(6,stopFreq);
                preparedStatement.setString(7,rbw);
                preparedStatement.setString(8,refLevel);
                preparedStatement.setString(9,att);
                preparedStatement.setString(10,gain);
                preparedStatement.setString(11,pointNum);
                preparedStatement.setString(12,segmentNum);
                preparedStatement.setString(13,btraceName);
                preparedStatement.setString(14,ethreshold);
                preparedStatement.setString(15,indexName);
                preparedStatement.setString(16,compoundCode);
                preparedStatement.setString(17, data);
                //preparedStatement.setList<Double>(17,data);


//                preparedStatement.setInt(2, userid);
//                preparedStatement.setFloat(3, price);
//                preparedStatement.setString(4, creattime);
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }

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
        private List<Double> data;
    }
}

