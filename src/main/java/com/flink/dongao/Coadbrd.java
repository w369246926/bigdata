package com.flink.dongao;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Coadbrd {
    public static void main(String[] args) throws Exception {
        //环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //数据源
        //创建Map集合 ：freqMap
        HashMap<Double, Integer> freqMap = new HashMap<Double, Integer>();
        //创建Map集合 ：freqDisMap
        HashMap<Double, Integer> freqDisMap = new HashMap<Double, Integer>();
        //创建Map集合 ：paraMap
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        //向paraMap集合中添加数据
        paraMap.put("mode", "2");// 带有统计功能的汇聚设备
        System.out.println(paraMap);
        System.out.println("--------");

        //用getDeviceListByMode判断paraMap集合是否为null？
        //List<DeviceForm> deviceList = FrontCommonUtils.getDeviceListByMode(paraMap);
        DataStream<String> ds2 = env.fromCollection(Arrays.asList("mode", "2"));
        //ds2.print();

        //计算


        //落地-


        //关闭
        env.execute();
    }
}
