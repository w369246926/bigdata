package com.flink.sockettest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class tcptest {

        public static void main(String[] args) throws IOException {
            //创建客户端的Socket对象(Socket)
            //Socket(String host, int port) 创建流套接字并将其连接到指定主机上的指定端口号
            Socket s = new Socket("10.10.42.241",20188);

            //获取输出流，写数据
            //OutputStream getOutputStream() 返回此套接字的输出流
            OutputStream os = s.getOutputStream();
            os.write("hello,tcp".getBytes());

            //释放资源
            s.close();
        }
    }
