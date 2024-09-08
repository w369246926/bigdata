package com.flink.flumeSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class sotk {

    public static void main(String[] args) {
        try {
            // 创建一个 ServerSocket ，绑定监听端口为 8080
            ServerSocket serverSocket = new ServerSocket(9998);
            // 调用 accept() 方法监听客户端请求，该方法是阻塞方法，程序会停留在这里直到有客户端请求服务端的 8080 接口
            //Socket 用于通信中的数据传输
            Socket socket = serverSocket.accept();
            // 创建 BufferedReader 接收来自客户端的请求数据，源数据流对象来自客户端发起请求的 Socket
            while (true) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String requestData = bufferedReader.readLine();
                System.out.println(" 接收到客户端的请求数据： " + requestData);

                // 创建 PrintWriter 发送服务端响应数据，接收数据流对象也来自 Socket
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                // 写入响应数据
                printWriter.println(" 服务端已接收到你的请求，响应数据为（服务端响应数据）！ ");
                // 使用 flush() 方法强制发送数据而不是等到缓冲区满了后才发送
                printWriter.flush();
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
