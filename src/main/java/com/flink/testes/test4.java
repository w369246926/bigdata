package com.flink.testes;

import jdk.internal.instrumentation.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class test4 {
    public static void main(String[] args) throws IOException {

        String AA = "123";
        String BB = "234";
        int cc = 123;
        int dd = 234;

        if (AA == BB ){
            System.out.println(123);
        };
        if (AA .equals(BB)  ){
            System.out.println(123);
        }
        //读取空闲的可用端口
        //extracted();
        //System.out.println(getFreePort());
    }

    private static void extracted() throws IOException {
        ServerSocket serverSocket = new ServerSocket(0);
        int port = serverSocket.getLocalPort();
        System.out.println("获取空闲端口：" + port);
        serverSocket.close();
        System.out.println(port);
    }
    public static List getFreePort() throws IOException {//获取空闲端口
        ServerSocket tmp;
        int i=1;
        ArrayList al = null;
        for(;i<=65536;i++){

                tmp=new ServerSocket(i);
                tmp.close();
                tmp=null;
                al.add(i);

            }
        return al;
        }


    }

