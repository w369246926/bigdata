package com.flink.sockettest;

import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.stream.IntStream;

public class TelnetClient2 {
    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        TelnetThread telnetThread0 = new TelnetThread();
        long start = System.currentTimeMillis();
        //for (int i = 0; i < 10; i++) {
            telnetThread0.run();
       //     }
        long stop = System.currentTimeMillis();
        System.out.println("开始时间："+start+"结束时间："+stop);
        //Thread.sleep(50*1000);


        }
        //extracted(sjs);

    private static void extracted(int sjs) {
        try {

            Socket socket = new Socket("172.16.153.5", 29997);
            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            PrintWriter pw = new PrintWriter(os);
            pw.println("id-'"+sjs+"'wifisourceandsourcesourcesourcesourcesource");
            pw.flush();
            String response = br.readLine();
            System.out.println(response);

            pw.close();
            br.close();
            is.close();
            os.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}