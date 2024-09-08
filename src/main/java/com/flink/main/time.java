package com.flink.main;

import java.text.SimpleDateFormat;
import java.util.Date;

public class time {
    public static void main(String[] args) {
        //String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

}
