//package com.flink.testes;
//
//import com.ververica.cdc.connectors.mysql.source.MySqlSource;
//import com.ververica.cdc.connectors.mysql.table.StartupOptions;
//import com.ververica.cdc.debezium.JsonDebeziumDeserializationSchema;
//import org.apache.flink.api.common.eventtime.WatermarkStrategy;
//import org.apache.flink.streaming.api.datastream.DataStreamSource;
//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//public class test2 {
//    public static void main(String[] args) throws Exception {
//
//
//            StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//            env.setParallelism(1);
//
//            MySqlSource<String> mySqlSource = MySqlSource.<String>builder()
//                    .hostname("node1")
//                    .port(3306)
//                    .username("root")
//                    .password("123456")
//                    .databaseList("test")
//                    .tableList("test.tbl1")
//                    .startupOptions(StartupOptions.initial())
//                    .deserializer(new JsonDebeziumDeserializationSchema())
//                    .build();
//            DataStreamSource<String> mysqlSourceDS = env.fromSource(mySqlSource,
//                    WatermarkStrategy.noWatermarks(),
//                    "MysqlSource");
//
//            mysqlSourceDS.print(">>>>>>");
//
//            env.execute();
//        }
//    }
