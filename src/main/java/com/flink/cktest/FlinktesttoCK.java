package com.flink.cktest;

//import cn.itcast.connectors.ckSink;
//import org.apache.flink.api.common.RuntimeExecutionMode;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

/**
 * Author itcast
 * Desc 演示Flink-Connectors-KafkaComsumer/Source
 */
public class FlinktesttoCK {
    public static void main(String[] args) throws Exception {
        //TODO 0.env
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC);

        //TODO 1.source
        DataStream<String> ds1 = env.readTextFile("data/input/words.txt");
        //TODO 2.transformation
        DataStream<Tuple4<String, String, String, String>> dataStream = ds1.map(new MapFunction<String, Tuple4<String, String, String, String>>() {
            @Override
            public Tuple4<String, String, String, String> map(String s) throws Exception {
                String[] split = s.split(",");
                String oder_id = split[0];
                String user_id = split[1];
                String price = split[2];
                String creat_time = split[3];
                return new Tuple4<>(oder_id, user_id, price, creat_time);
            }
        });

        //TODO 3.sink
        //dataStream.print();
        dataStream.addSink(new ckSink());
        //dataStream.print();

        //TODO 4.execute
        env.execute();
    }


    private static class ckSink extends RichSinkFunction<Tuple4<String, String, String, String>> {
        private Connection conn;
        private PreparedStatement preparedStatement;

        @Override
        public void open(Configuration parameters) throws Exception {
            super.open(parameters);
            //加载JDBC驱动
            Class.forName("ru.yandex.clickhouse.ClickHouseDriver");
            //获取数据库连接
            conn = DriverManager.getConnection("jdbc:clickhouse://192.168.88.162:8123/default");
            //String sql = "INSERT INTO `user_order_all` (`oder_id`, `user_id`, `price`, `creat_time`) VALUES (?,?,?,?);";
            //conn.prepareStatement(sql);
            preparedStatement = conn.prepareStatement("INSERT INTO trace(keywords,eventType,traceInTime,deviceId) VALUES (?,?,?,?)");

        }

        @Override
        public void close() throws Exception {
            super.close();
            if (preparedStatement != null) {

                preparedStatement.close();
            }
        }

        @Override
        public void invoke(Tuple4<String, String, String, String> value, Context context) throws Exception {
            try {
                String keywords = value.f0;
                String eventType = value.f1;
                String traceInTime = value.f2;
                String deviceId = value.f3;
                preparedStatement.setString(1, keywords);
                preparedStatement.setString(2, eventType);
                preparedStatement.setString(3, traceInTime);
                preparedStatement.setString(4, deviceId);
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

}
//准备主题 /export/server/kafka/bin/kafka-topics.sh --create --zookeeper node1:2181 --replication-factor 2 --partitions 3 --topic flink_kafka
//启动控制台生产者发送数据 /export/server/kafka/bin/kafka-console-producer.sh --broker-list node1:9092 --topic flink_kafka
//启动程序FlinkKafkaConsumer
//观察控制台输出结果