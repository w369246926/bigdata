package com.flink.flumeSocket;

import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeoutException;

public class Sockettest {
    //1.rabbitmq创建连接工厂


    public static void main(String[] args) throws IOException, TimeoutException {
        //1.rabbitmq创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //2. 设置参数
        //3. 创建连接 Connection
        Connection connection = factory.newConnection();
        //4. 创建Channel
        Channel channel = connection.createChannel();
        Channel channel2 = connection.createChannel();
        //5. 创建队列Queue
        //如果没有一个名字叫hello_world的队列，则会创建该队列，如果有则不会创建
        channel.queueDeclare("Trace", true, false, false, null);
        // 创建一个 ServerSocket ，绑定监听端口为 8080
        ServerSocket serverSocket = new ServerSocket(9997);

        try {
            // 调用 accept() 方法监听客户端请求，该方法是阻塞方法，程序会停留在这里直到有客户端请求服务端的 8080 接口
            //Socket 用于通信中的数据传输
            while (true) {
                Socket socket = serverSocket.accept();
                // 创建 BufferedReader 接收来自客户端的请求数据，源数据流对象来自客户端发起请求的 Socket
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String requestData = bufferedReader.readLine();
                //writerabbitmq(requestData);
                System.out.println(" 以接收： " + requestData);
                //定义body信息
                String body = requestData;
                //6. 发送消息
                channel.basicPublish("", "hello_world", null, body.getBytes());

                //4. 创建Channel

                // 接收消息
                Consumer consumer = new DefaultConsumer(channel2) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                        System.out.println("以消费：" + new String(body));
                    }
                };
                channel.basicConsume("hello_world", true, consumer);


                // 创建 PrintWriter 发送服务端响应数据，接收数据流对象也来自 Socket
                //PrintWriter printWriter = new PrintWriter(socket.getOutputStream()) ;
                // 写入响应数据
                //printWriter.println( " 服务端已接收到你的请求，响应数据为（服务端响应数据）！ " ) ;
                // 使用 flush() 方法强制发送数据而不是等到缓冲区满了后才发送
                //printWriter.flush() ;
                socket.close();
            }
            // 关闭资源
            //printWriter.close() ;
            //bufferedReader.close() ;
            //socket.close() ;
            //serverSocket.close() ;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

