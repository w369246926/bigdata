package com.flink.window;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

public class Demo1 {
        public static void main(String[] args) throws Exception {
            //TODO 0.env
            StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
            //env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC);
            //TODO 1.source
            DataStream<String> lines = env.socketTextStream("10.10.41.169", 9092);
            //TODO 2.transformation
            SingleOutputStreamOperator<CartInfo> cards = lines.map(new MapFunction<String, CartInfo>() {
                @Override
                public CartInfo map(String s) throws Exception {
                    String[] split = s.split(",");
                    return new CartInfo(split[0], Integer.parseInt(split[1]));
                }
            });
            //KeyedStream<CartInfo, String> cartInfoStringKeyedStream = cards.keyBy(cartInfo -> cartInfo.getSensorId());
            KeyedStream<CartInfo, String> keyds = cards.keyBy(CartInfo::getSensorId);
            SingleOutputStreamOperator<CartInfo> count = keyds.window(TumblingProcessingTimeWindows.of(Time.seconds(5))).sum("count");
            SingleOutputStreamOperator<CartInfo> count2 = keyds.window(SlidingProcessingTimeWindows.of(Time.seconds(10), Time.seconds(5))).sum("count2");
            //TODO 3.sink
            count.print();
            //count2.print();
            //TODO 4.execute
            env.execute();

        }
        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class CartInfo {
            private String sensorId;//信号灯id
            private Integer count;//通过该信号灯的车的数量
        }
    }
