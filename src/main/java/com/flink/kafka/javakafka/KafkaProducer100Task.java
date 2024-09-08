package com.flink.kafka.javakafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.UUID;

public class KafkaProducer100Task implements Runnable {
    private final String topic;

    public KafkaProducer100Task(String topic) {
        this.topic = topic;

    }

    @Override
    public void run() {
        //UUID  为每次不重复数据
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString();
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.88.161:9092");
        props.put("bootstrap.servers", "192.168.88.162:9092");
        props.put("bootstrap.servers", "192.168.88.163:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        String key = "key-" + s;
        String message ="WIFIProbe|1678256409406|1201|VUZJXzYyMDg=|86:99:C7:B4:86:E9|WPA2-PSK||FF:FF:FF:FF:FF:FF|6|00|08|-50|2.3|0|160|128|||||abcd1243|WIFIProbe-2023-03-08 ";
        producer.send(new ProducerRecord<>(topic,key, message));
        System.out.println("当前"+s+"线程执行完毕");
        producer.close();
    }
}
