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
public class FlinkKafkaComsumertoCK {
    public static void main(String[] args) throws Exception {
        //TODO 0.env
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC);

        //TODO 1.source
        //准备kafka连接参数
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "192.168.88.161:9092");//集群地址
        props.setProperty("group.id", "flink_kafka");//消费者组id
        //props.setProperty("auto.offset.reset","latest");//latest有offset记录从记录位置开始消费,没有记录从最新的/最后的消息开始消费 /earliest有offset记录从记录位置开始消费,没有记录从最早的/最开始的消息开始消费
        //props.setProperty("flink.partition-discovery.interval-millis", "5000");//会开启一个后台线程每隔5s检测一下Kafka的分区情况,实现动态分区检测
        //props.setProperty("enable.auto.commit", "true");//自动提交(提交到默认主题,后续学习了Checkpoint后随着Checkpoint存储在Checkpoint和默认主题中)
        //props.setProperty("auto.commit.interval.ms", "2000");//自动提交的时间间隔
        //使用连接参数创建FlinkKafkaConsumer/kafkaSource
        FlinkKafkaConsumer<String> kafkaSource = new FlinkKafkaConsumer<String>("t1703", new SimpleStringSchema(), props);
        //使用kafkaSource
        DataStream<String> kafkaDS = env.addSource(kafkaSource);
        kafkaDS.print();
        //TODO 2.transformation
        DataStream<Tuple4<Integer, Integer, Float, String>> dataStream = kafkaDS.map(new MapFunction<String, Tuple4<Integer, Integer, Float, String>>() {
            @Override
            public Tuple4<Integer, Integer, Float, String> map(String s) throws Exception {
                String[] split = s.split(",");
                int oder_id = Integer.parseInt(split[0]);
                int user_id = Integer.parseInt(split[1]);
                float price = Float.parseFloat(split[2]);
                String creat_time = split[3];
                return new Tuple4<>(oder_id, user_id, price, creat_time);
            }
        });

        //TODO 3.sink
        //dataStream.print();
        dataStream.addSink(new ckSink());

        //TODO 4.execute
        env.execute();
    }


    private static class ckSink extends RichSinkFunction<Tuple4<Integer, Integer, Float, String>> {
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
            preparedStatement = conn.prepareStatement("INSERT INTO userorderall1(oderid,userid,price,creattime) VALUES (?,?,?,?)");
        }

        @Override
        public void close() throws Exception {
            super.close();
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

        @Override
        public void invoke(Tuple4<Integer, Integer, Float, String> value, Context context) throws Exception {
            try {
                int oderid = value.f0;
                int userid = value.f1;
                float price = value.f2;
                String creattime = value.f3;
                preparedStatement.setInt(1, oderid);
                preparedStatement.setInt(2, userid);
                preparedStatement.setFloat(3, price);
                preparedStatement.setString(4, creattime);
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