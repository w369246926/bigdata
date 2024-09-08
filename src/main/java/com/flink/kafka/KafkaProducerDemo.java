package com.flink.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class KafkaProducerDemo {
    public static void main(String[] args) {
        // Kafka 集群的地址和端口号
        //String bootstrapServers = "localhost:9092";

        // Kafka 主题名称
        String topicName = "my-topic3";

        // 创建 Kafka 生产者配置对象
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

        // 创建 Kafka 生产者对象
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        // 创建一个线程池，用于执行发送数据的任务
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // 在线程池中创建一个任务，该任务的功能是向 Kafka 发送数据
        executor.submit(() -> {
            try {
                // 发送数据
                for (int i = 0; i < 10; i++) {
                    String message = "Message " + i;
                    ProducerRecord<String, String> record = new ProducerRecord<>(topicName, message);
                    producer.send(record);
                    System.out.println("Sent message: " + message);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 等待 3 秒钟后，停止发送数据的任务
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
            try {
                executor.awaitTermination(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            producer.close();
        }
    }
}
