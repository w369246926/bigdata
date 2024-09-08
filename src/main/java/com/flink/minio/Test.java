package com.flink.minio;

import io.minio.*;

import java.io.*;

public class Test {
    public static void main(String[] args) throws Exception {
        MinioClient minioClient =
//                MinioClient.builder()
//                        .endpoint("http://10.10.41.242:9292")
//                        .credentials("minioadmin", "minioadmin")
//                        .build();
        MinioClient.builder()
                .endpoint("http://192.168.88.161:9080")
                .credentials("adminminio", "adminminio")
                .build();
//                .endpoint("http://172.21.0.4:9000")
//                .credentials("WY0AkYNwhWZzMq2y", "ibFz6ZAvGWOh9vMPb7MxHuvh2x3IZzTn")
//                .build();

        boolean file = minioClient.bucketExists(BucketExistsArgs.builder().bucket("file").build());
        System.out.println("file");
            //创建桶7
            //minioClient.makeBucket(MakeBucketArgs.builder().bucket("bucket").build());
        InputStream stream = new FileInputStream("D:\\微信截图.png");
        //上传文件
        minioClient.putObject(PutObjectArgs.builder().bucket("file").object("微信截图.png")
                .stream(stream, -1, 10485760).build());
        //下载
        //InputStream stream2 = minioClient.getObject(
        //        GetObjectArgs.builder().bucket("bucket").object("微信图.png").build());
//        int index;
//        byte[] bytes = new byte[1024];
//        FileOutputStream downloadFile = new FileOutputStream("D:\\微信图222.png");
//        while ((index = stream2.read(bytes)) != -1) {
//            downloadFile.write(bytes, 0, index);
//            downloadFile.flush();
//        }
//        downloadFile.close();
        stream.close();
    }
}
