package com.flink.io;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.Arrays;

public class BmpToChar {
    public static void main(String[] args) throws Exception {
        // 读取bmp文件
        FileInputStream fis = new FileInputStream("test.bmp");
        // 读取文件流
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int n;
        while ((n = fis.read(b)) != -1) {
            bos.write(b, 0, n);
        }
        fis.close();
        bos.close();
        // 获取文件字节数组
        byte[] bytes = bos.toByteArray();
        // 将字节数组转换成char数组
        char[] chars = new char[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            chars[i] = (char) bytes[i];
        }
        // 打印char数组
        System.out.println(Arrays.toString(chars));
    }
}
