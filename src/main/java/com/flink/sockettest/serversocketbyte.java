package com.flink.sockettest;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class serversocketbyte {

        public static void main(String args[]) throws IOException {
            //为了简单起见，所有的异常信息都往外抛
            int port = 20182;
            //定义一个ServerSocket监听在端口8899上
            ServerSocket server = new ServerSocket(port);
            while (true) {
                //server尝试接收其他Socket的连接请求，server的accept方法是阻塞式的
                Socket socket = server.accept();
                //每接收到一个Socket就建立一个新的线程来处理它
                new Thread(new Task(socket)).start();
            }
        }

        /**
         * 用来处理Socket请求的
         */
        static class Task implements Runnable {

            private Socket socket;

            public Task(Socket socket) {
                this.socket = socket;
            }

            @Override
            public void run() {
                try {
                    handleSocket();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            /**
             * 跟客户端Socket进行通信
             * @throws Exception
             */
            private void handleSocket() throws Exception {
                InputStream inputStream = socket.getInputStream();

                byte[] bytes = new byte[4];
                inputStream.read(bytes);

                /*BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "GBK"));
                StringBuilder sb = new StringBuilder();
                String temp;
                int index;
                while ((temp=br.readLine()) != null) {
                    System.out.println(temp);
                    if ((index = temp.indexOf("eof")) != -1) {//遇到eof时就结束接收
                        sb.append(temp.substring(0, index));
                        break;
                    }
                    sb.append(temp);
                }*/
                //System.out.println("客户端: " + sb);
                //读完后写一句
                Writer writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
                writer.write("你好，客户端。");
                writer.write("eof\n");
                writer.flush();
                writer.close();
                //br.close();
                socket.close();
            }
        }
    }
