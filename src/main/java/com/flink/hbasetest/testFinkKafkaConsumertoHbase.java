package com.flink.hbasetest;

import lombok.extern.slf4j.Slf4j;
//import org.apache.flink.api.common.RuntimeExecutionMode;
import jdk.nashorn.internal.runtime.ErrorManager;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple4;
//import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.util.Bytes;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

/**
 * Author itcast
 * Desc 演示Flink-Connectors-KafkaComsumer/Source
 */
@Slf4j
public class testFinkKafkaConsumertoHbase {
    public static void main(String[] args) throws Exception {
        //TODO 0.env
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC);

        //TODO 1.source
        //准备kafka连接参数
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "10.10.41.169:9092");//集群地址
        props.setProperty("group.id", "flink_kafka");//消费者组id

        FlinkKafkaConsumer<String> kafkaSource = new FlinkKafkaConsumer<String>("t1704", new SimpleStringSchema(), props);
        //使用kafkaSource
        DataStream<String> kafkaDS = env.addSource(kafkaSource);
        kafkaDS.print("kafka数据");
        //TODO 2.transformation
        /*DataStream<Tuple4<Integer, Integer, Float, String>> dataStream = kafkaDS.map(new MapFunction<String, Tuple4<Integer, Integer, Float, String>>() {
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
        dataStream.print("map后数据");*/
        //dataStream.addSink(new ckSink());
        //kafkaDS.addSink(new HbaseSink());


        //TODO 4.execute
        env.execute();
    }

    private static class HbaseSink implements SinkFunction<Tuple4<Integer, Integer, Float, String>> {
        public void invoke(String value, Context context) throws Exception {
            Connection connection = null;
            Table table = null;
            try {
                // 加载HBase的配置
                Configuration configuration = HBaseConfiguration.create();

                // 读取配置文件
                configuration.addResource(new Path(ClassLoader.getSystemResource("hbase-site.xml").toURI()));
                configuration.addResource(new Path(ClassLoader.getSystemResource("core-site.xml").toURI()));

                connection = ConnectionFactory.createConnection(configuration);

                TableName tableName = TableName.valueOf("trace");

                // 获取表对象
                table = connection.getTable(tableName);

                //row1:cf:a:aaa
                String[] split = value.split(":");

                // 创建一个put请求，用于添加数据或者更新数据
                Put put = new Put(Bytes.toBytes(split[0]));
                put.addColumn(Bytes.toBytes(split[1]), Bytes.toBytes(split[2]), Bytes.toBytes(split[3]));
                table.put(put);
                log.error("[HbaseSink] : put value:{} to hbase", value);
            } catch (Exception e) {
                log.error("", e);
            } finally {
                if (null != table) table.close();
                if (null != connection) connection.close();
            }
        }
    }
}