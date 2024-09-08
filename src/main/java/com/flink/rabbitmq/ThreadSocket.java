package com.flink.rabbitmq;

import com.flink.sockettest.socket;
import com.rabbitmq.client.*;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class ThreadSocket implements Runnable {
    private Socket acceptSocket;

    public ThreadSocket(Socket accept) {
        this.acceptSocket = accept;
    }

    @Override
    public void run() {
        byte[] bytes = new byte[1024*8];
        BufferedReader br = null;
        BufferedOutputStream bos = null;
        //1.rabbitmq创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        try {
            //2. 设置参数
            //3. 创建连接 Connection
            Connection connection = factory.newConnection();
            //4. 创建Channel
            Channel channel = connection.createChannel();
            Channel channel2 = connection.createChannel();
            //5. 创建队列Queue
            //如果没有一个名字叫hello_world的队列，则会创建该队列，如果有则不会创建
            channel.queueDeclare("Trace", true, false, false, null);

            //创建 BufferedReader 接收来自客户端的请求数据，源数据流对象来自客户端发起请求的 Socket
            br = new BufferedReader(new InputStreamReader(acceptSocket.getInputStream()));

            //网络中的流,从客户端读取数据的
//            BufferedInputStream bis = new BufferedInputStream(acceptSocket.getInputStream());
//            //本地的IO流,把数据写到本地中,实现永久化存储
//            //bos = new BufferedOutputStream(new FileOutputStream("d:/" + UUID.randomUUID().toString() + ".txt"));
//            bos = new BufferedOutputStream(new FileOutputStream("d:/" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".txt"));
//
//            int b;
//            while((b = bis.read())!=-1) {
//                bos.write(b);//通过网络写到服务器中
//                //同步到RabbitMQ的hello_world消息队列
                String brl = br.readLine();
                System.out.println(brl);

                //6. 发送消息
                channel.basicPublish("", "Trace", null,brl.getBytes());

/*                // 接收消费消息
                Consumer consumer = new DefaultConsumer(channel2) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                        System.out.println("以消费：" + new String(body));
                    }
                };
                channel.basicConsume("hello_world", true, consumer);*/


            /*BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(acceptSocket.getOutputStream()));
            bw.write("上传成功");
            bw.newLine();
            bw.flush();*/


        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            /*if(br != null ){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/

            if(bos != null ){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (acceptSocket != null){
                try {
                    acceptSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
