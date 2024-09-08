package com.flink.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class inandout {
        public static void main(String[] args) {
            try {
                //创建文件对象
                File file = new File("D:\\test.txt");
                //创建文件输入流
                FileInputStream fis = new FileInputStream(file);
                //创建文件输出流
                FileOutputStream fos = new FileOutputStream("D:\\test1.txt");
                //创建缓冲区
                byte[] b = new byte[1024];
                int len = 0;
                //读取文件
                while ((len = fis.read(b)) != -1) {
                    //写入文件
                    fos.write(b, 0, len);
                }
                //关闭流
                fis.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
