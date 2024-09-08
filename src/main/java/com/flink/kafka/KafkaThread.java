package com.flink.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.*;
import java.net.Socket;
import java.util.Properties;
import java.util.Random;

public class KafkaThread extends Thread{
    String topicName = "my-topic2";
    Properties props = new Properties();
//        props.put("bootstrap.servers", "192.168.88.161:9092");
//        props.put("bootstrap.servers", "192.168.88.162:9092");
//        props.put("bootstrap.servers", "192.168.88.163:9092");
//        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    org.apache.kafka.clients.producer.KafkaProducer<String, String> producer = new org.apache.kafka.clients.producer.KafkaProducer<>(props);
    int i = 0;
    String key = "key-" ;
    String value = "value-" ;
    ProducerRecord<String, String> record = new ProducerRecord<>(topicName, key, value);
    //定义开始时间
    long start = System.currentTimeMillis();

    @Override
    public void run() {
        //循环3秒
        while(System.currentTimeMillis() - start < 3000){
            key = "key-" + i;
            value = "value-" + i;
            System.out.println(i++);
            producer.send(record);
        }
        producer.close();
    }
}
