package com.flink.testes;

import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaProducer {
    public static void main(String[] args) {
        String topicName = "my-topic2";
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.88.161:9092");
        props.put("bootstrap.servers", "192.168.88.162:9092");
        props.put("bootstrap.servers", "192.168.88.163:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        org.apache.kafka.clients.producer.KafkaProducer<String, String> producer = new org.apache.kafka.clients.producer.KafkaProducer<>(props);
        int i = 0;
        String key = "key-" + i;
        String value = "value-" + i  ;
        i++;
        ProducerRecord<String, String> record = new ProducerRecord<>(topicName, key, value);
        //定义开始时间
        long start = System.currentTimeMillis();
        //循环3秒
        while(System.currentTimeMillis() - start < 3000){
            producer.send(record);
            System.out.println(i);
        }
            producer.close();
    }
}
