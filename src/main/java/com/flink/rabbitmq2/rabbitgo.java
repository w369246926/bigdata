package com.flink.rabbitmq2;

import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeoutException;

public class rabbitgo {
    public static void main(String[] args) throws IOException, TimeoutException {

        //1.rabbitmq创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //2. 设置参数
        //3. 创建连接 Connection
        Connection connection = factory.newConnection();
        //4. 创建Channel
        Channel channel = connection.createChannel();
        //4. 创建Channel
        Channel channel2 = connection.createChannel();
        //5. 创建队列Queue
        //如果没有一个名字叫hello_world的队列，则会创建该队列，如果有则不会创建
        channel.queueDeclare("Trace", true, false, false, null);

        // 创建一个 ServerSocket ，绑定监听端口为 8080
        System.out.println("绑定监听端口为 9997");
        ServerSocket serverSocket = new ServerSocket(9997);
        // 调用 accept() 方法监听客户端请求，该方法是阻塞方法，程序会停留在这里直到有客户端请求服务端的 8080 接口
        //Socket 用于通信中的数据传输
        System.out.println("正在接收来自客户端的请求数据......");
        Socket socket = serverSocket.accept();


        // 创建 BufferedReader 接收来自客户端的请求数据，源数据流对象来自客户端发起请求的 Socket
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String requestData = bufferedReader.readLine();
            //writerabbitmq(requestData);
            System.out.println(" 以接收： " + requestData);
            //定义body信息
            String body = requestData;
            //6. 发送消息

            channel.basicPublish("", "Trace", null, body.getBytes());


            // 接收消息
            Consumer consumer = new DefaultConsumer(channel2) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                    System.out.println("以消费：" + new String(body));
                }
            };

                channel.basicConsume("Trace", true, consumer);



        }
    }

