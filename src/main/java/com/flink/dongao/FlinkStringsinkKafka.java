package com.flink.dongao;

//import org.apache.flink.api.common.RuntimeExecutionMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.util.Collector;

import java.util.Properties;
import java.util.Random;

public class FlinkStringsinkKafka {

    public static void main(String[] args) throws Exception {
        //创建flink环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC);
        //source数据源
        DataStream<String> streamSource = env.readTextFile("data/input/words1.txt").setParallelism(1);




        //sink kafka
        streamSource.print();

        Properties props2 = new Properties();
        props2.setProperty("bootstrap.servers", "192.168.88.128:6667");
        FlinkKafkaProducer<String> kafkaSink = new FlinkKafkaProducer<String>("da_trace", new SimpleStringSchema(), props2);

        streamSource.addSink(kafkaSink);

        /*Properties props2 = new Properties();
        props2.setProperty("bootstrap.servers", "192.168.88.161:9092");
        FlinkKafkaProducer<Tuple4<Integer, Integer, Float, String>> kafkaSink = new FlinkKafkaProducer<Tuple4<Integer, Integer, Float, String>>("t1703", (KeyedSerializationSchema<Tuple4<Integer, Integer, Float, String>>) new SimpleStringSchema(), props2);
        flatMap.addSink(kafkaSink);*/

        //execute
        env.execute();
    }




/*    @Data
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
    }*/
}
