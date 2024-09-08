package com.flink.kafka;

import com.flink.kafka.javakafka.KafkaProducer100Task;
import com.flink.kafka.javakafka.KafkaProducerTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KafkaProducer {
    //开启线程数
    private static final int NUM_NODES = 5;
    //卡夫卡 消息队列名称
    private static final String KAFKA_TOPIC = "test3";
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_NODES);
        for (int i = 0; i < NUM_NODES; i++) {
            System.out.println("第"+i+"线程执行了");
            //用于测试100万条数据量
            executor.execute(new KafkaProducerTask(KAFKA_TOPIC));
            //用于测试100个节点并发量
            //executor.execute(new KafkaProducer100Task(KAFKA_TOPIC));
        }
        executor.shutdown();
    }
}
