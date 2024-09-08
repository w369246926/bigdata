package com.flink.time;

import org.springframework.scheduling.annotation.Scheduled;

public class TimeThree {
    @Scheduled(cron = "0/5 * * * * ?")
    public void test(){
        System.out.println("定时器执行了");
    }
}

