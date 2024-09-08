package com.flink.thread;

public class StartThread {
    public static void main(String[] args) {
        //todo 1: 使用线程 先new对象
        MyThread myThread = new MyThread();
        //todo 2: 单独启动线程运行(多线程)
        myThread.start();
        //todo 3: 直接调用run,并非单独启动多线程(单线程)
        myThread.run();
        //todo 4: 提取变量
        String name = myThread.name;
    }

}

