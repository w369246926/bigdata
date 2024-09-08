package com.flink.sockettest;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class TelnetThread extends Thread{

    @Override
    public void run() {
        Socket socket = null;
        try {
            socket = new Socket("172.16.153.5", 29997);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < 50000; i++) {
            Random random = new Random();
            int sjs = random.nextInt(10000) % (10000 - 0 + 1) + 1;

            try {
                OutputStream os = socket.getOutputStream();
                InputStream is = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                PrintWriter pw = new PrintWriter(os);

                pw.println("id-"+sjs+"WIFIProbe|1678256409406|1201|VUZJXzYyMDg=|86:99:C7:B4:86:E9|WPA2-PSK||FF:FF:FF:FF:FF:FF|6|00|08|-50|2.3|0|160|128|||||abcd1243|WIFIProbe-2023-03-08");
                pw.flush();

                String response = br.readLine();
                System.out.println("发送情况："+response+"---发送第："+(i+1)+"---随机编号："+sjs);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
