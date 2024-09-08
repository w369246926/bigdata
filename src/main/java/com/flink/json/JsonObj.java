package com.flink.json;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
//import org.json.JSONArray;
//import org.json.JSONObject;

import static com.flink.dateformat.DateUtil.*;

public class JsonObj {
    public static void main(String[] args) {
        /**
         * 实现步骤：
         * 1）定义json字符串
         * 2）创建javaBean对象及定义属性
         * 3）使用jsonObject进行解析json字符串
         */

        //TODO 1）定义json字符串
        String jsonStr = "{\"batteryAlarm\": 0,\"carMode\": 1,\"minVoltageBattery\": 3.89,\"chargeStatus\": 1,\"vin\": \"LS5A3CJC0JF890971\",\"nevChargeSystemTemperatureDtoList\": [{\"probeTemperatures\": [25, 23, 24, 21, 24, 21, 23, 21, 23, 21, 24, 21, 24, 21, 25, 21],\"chargeTemperatureProbeNum\": 16,\"childSystemNum\": 1}]}";

        //TODO 2）创建javaBean对象及定义属性
        JSONObject jsonObject = new JSONObject(JSON.parseObject(jsonStr));
        //int batteryAlarm = jsonObject.getInt("batteryAlarm");
        Long batteryAlarm1 = jsonObject.getLong("batteryAlarm");
        Long carMode = jsonObject.getLong("carMode");
        double minVoltageBattery = jsonObject.getDouble("minVoltageBattery");
        Long chargeStatus = jsonObject.getLong("chargeStatus");
        String vin = jsonObject.getString("vin");

        System.out.println(batteryAlarm1 +"/n"+ carMode +"/n"+  minVoltageBattery +"/n"+ chargeStatus +"/n"+ vin);

        //获取数组对象
        //2.1：第一次解析参数
        //JSONArray nevChargeSystemTemperatureDtoList =

        System.out.println(getCurrentDateTime());
        System.out.println(getCurrentDate());
        System.out.println(convertStringToDate("20210301"));
        System.out.println(convertStringToDateTime("2021-03-01 10:21:32"));
        System.out.println(convertStringToDateString("2021-03-01 10:21:32"));
    }
}
