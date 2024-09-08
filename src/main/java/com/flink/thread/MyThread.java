package com.flink.thread;


//todo 1: 创建线程需要继承Thread类
public class MyThread extends Thread{

    //可以定义变量,后期使用

    String name = null;
    //todo 2: 实现run方法
    @Override
    public void run() {
        //todo 3: 实现具体业务逻辑
        for (int i = 0; i < 100; i++) {
            System.out.println(i);
        }
        name = "zhangsan";
    }
}
