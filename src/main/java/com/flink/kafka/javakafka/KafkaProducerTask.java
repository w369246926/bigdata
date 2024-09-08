package com.flink.kafka.javakafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaProducerTask implements Runnable {
    private final String topic;

    public KafkaProducerTask(String topic) {
        this.topic = topic;

    }

    @Override
    public void run() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "hadoop104:9092");
        props.put("bootstrap.servers", "hadoop105:9092");
        props.put("bootstrap.servers", "hadoop106:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        long starttime = System.currentTimeMillis();
        //循环3秒
        while(System.currentTimeMillis() - starttime < 3000){
            String message = "WIFIProbe|1678256409406|1201|VUZJXzYyMDg=|86:99:C7:B4:86:E9|WPA2-PSK||FF:FF:FF:FF:FF:FF|6|00|08|-50|2.3|0|160|128|||||abcd1243|WIFIProbe-2023-03-08 ";
            producer.send(new ProducerRecord<>(topic, message));
        }
        System.out.println("线程执行完毕时间");
        producer.close();
    }
}
