package com.flink.time;

import java.util.Timer;
import java.util.TimerTask;

//java定时器多线程
public class Timetow {
    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("定时器执行了");
            }
        }, 0, 1000);
    }
}
