package com.flink.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.Properties;

public class KafkaProducerExample {
    public static void main(String[] args) {
        String topicName = "my-topic";
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.88.161:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        //循环100次
        for (int i = 0; i < 100; i++) {
            String key = "key-" + i;
            String value = "WIFIProbe|1678256409406|1201|VUZJXzYyMDg=|86:99:C7:B4:86:E9|WPA2-PSK||FF:FF:FF:FF:FF:FF|6|00|08|-50|2.3|0|160|128|||||abcd1243|WIFIProbe-2023-03-08" + i;
            //每次创建一个消息生产者对象
            ProducerRecord<String, String> record = new ProducerRecord<>(topicName, key, value);
            producer.send(record);
        }
        producer.close();
    }
}
