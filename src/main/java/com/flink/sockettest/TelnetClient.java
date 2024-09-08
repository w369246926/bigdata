package com.flink.sockettest;

import java.io.*;
import java.net.*;

public class TelnetClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("10.26.52.49", 9092);
            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            PrintWriter pw = new PrintWriter(os);
            pw.println("Hello, world!");
            pw.flush();
            String response = br.readLine();
            System.out.println(response);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
