package com.flink.testes;

//import org.apache.flink.api.common.RuntimeExecutionMode;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.serialization.SimpleStringEncoder;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.util.Collector;

import java.util.Properties;
import java.util.Random;

public class FlinkRandomsinkKafka {

    public static void main(String[] args) throws Exception {
        //创建flink环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC);
        //source数据源
        DataStreamSource<Oder> streamSource = env.addSource(new odersource()).setParallelism(2);
        //Transformation
        /*SingleOutputStreamOperator<String> flatMap = streamSource.flatMap(new FlatMapFunction<Oder, String>() {
            @Override
            public void flatMap(Oder s, Collector<String> collector) throws Exception {
                String id = String.valueOf(s.id);
                String userId = String.valueOf(s.userId);
                String money = String.valueOf(s.money);
                String createTime = s.createTime;
                String oder = id +"," +userId+"," + money+"," + createTime;
                collector.collect(oder);
            }
        });*/
         /*SingleOutputStreamOperator<Oder> filter = streamSource.filter(new FilterFunction<Oder>() {

            @Override
            public boolean filter(Oder o) throws Exception {
                return o.toString().contains("success");
            }
        });*/
        /*SingleOutputStreamOperator<Tuple4<Integer, Integer, Float, String>> flatMap = streamSource.flatMap(new FlatMapFunction<Oder, Tuple4<Integer, Integer, Float, String>>() {

            @Override
            public void flatMap(Oder oder, Collector<Tuple4<Integer, Integer, Float, String>> collector) throws Exception {
                Integer id = oder.id;
                Integer userId = oder.userId;
                Float money = oder.money;
                String createTime = oder.createTime;
                String s = oder.toString();
                collector.collect(Tuple4.of(id, userId, money, createTime));
            }
        } );*/


        //sink kafka
        streamSource.print();
        Properties props2 = new Properties();
        props2.setProperty("bootstrap.servers", "node1:9092");
        FlinkKafkaProducer<Oder> kafkaSink = new FlinkKafkaProducer<Oder>("t1703", new SerializationSchema<Oder>() {
            @Override
            public byte[] serialize(Oder oder) {
                return new byte[0];
            }
        }, props2);
        streamSource.addSink(kafkaSink);

        /*Properties props2 = new Properties();
        props2.setProperty("bootstrap.servers", "192.168.88.161:9092");
        FlinkKafkaProducer<Tuple4<Integer, Integer, Float, String>> kafkaSink = new FlinkKafkaProducer<Tuple4<Integer, Integer, Float, String>>("t1703", (KeyedSerializationSchema<Tuple4<Integer, Integer, Float, String>>) new SimpleStringSchema(), props2);
        flatMap.addSink(kafkaSink);*/

        //execute
        env.execute();
    }

    public static class Oder {
        private int id;
        private Integer userId;
        private float money;
        private String createTime;

        public Oder() {
        }

        public Oder(int id, int userid, float money, String createTime) {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public float getMoney() {
            return money;
        }

        public void setMoney(float money) {
            this.money = money;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        @Override
        public String toString() {
            return "Oder{" +
                    "id='" + id + '\'' +
                    ", userId=" + userId +
                    ", money=" + money +
                    ", createTime=" + createTime +
                    '}';
        }

    }

    private static class odersource extends RichParallelSourceFunction<Oder> {
        private Boolean flag = true;

        @Override
        public void run(SourceContext<Oder> sourceContext) throws Exception {
            Random random = new Random();
            while (flag) {
                Thread.sleep(5000);
                int id = random.nextInt(100);
                int userid = random.nextInt(3);
                float money = random.nextInt(1000);
                String createTime = String.valueOf(System.currentTimeMillis());
                Oder o = new Oder();
                o.id = id;
                o.userId = userid;
                o.money = money;
                o.createTime = createTime;
                sourceContext.collect(o);
                //sourceContext.collect(new Oder (id,userid,money,createTime));
            }
        }

        @Override
        public void cancel() {
            flag = false;
        }
    }
}
