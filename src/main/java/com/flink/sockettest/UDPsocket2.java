package com.flink.sockettest;

import java.net.DatagramPacket;

import java.net.DatagramSocket;

import java.net.InetAddress;

import java.net.SocketException;

public class UDPsocket2 {

    public static void main(String[] args) {

        try {

            DatagramSocket datagramSocket = new DatagramSocket();

            String data = "这是张佑发送的消息！";

            DatagramPacket datagramPacket = new DatagramPacket(data.getBytes(),data.getBytes().length,InetAddress.getLocalHost(),8088);

//发送

            System.out.println("正在准备发送……");

            datagramSocket.send(datagramPacket);

            System.out.println("已发送……");

            datagramSocket.close();

        } catch (Exception e) {

// TODO Auto-generated catch block

            e.printStackTrace();

        }

    }

}
