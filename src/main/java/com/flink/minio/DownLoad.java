//package com.flink.minio;
//
//import io.minio.DownloadObjectArgs;
//import io.minio.MinioClient;
//import io.minio.errors.MinioException;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//public class DownLoad {
//    public static void main(String[] args) throws IOException {
//// Create a minioClient with the MinIO server playground, its access keyand secret key.
//                MinioClient minioClient =
//                MinioClient.builder()
//                        .endpoint("http://10.10.41.242:9090")
//                        .credentials("minioadmin", "minioadmin")
//                        .build();
//// Download object given the bucket, object name and output file name
//        try {
//            // 调用statObject()来判断对象是否存在。
//            // 如果不存在, statObject()抛出异常,
//            // 否则则代表对象存在。
//            minioClient.statObject("mybucket", "myobject");
//
//            // 获取"myobject"的输入流。
//            InputStream stream = minioClient.getObject("mybucket", "myobject");
//
//            // 读取输入流直到EOF并打印到控制台。
//            byte[] buf = new byte[16384];
//            int bytesRead;
//            while ((bytesRead = stream.read(buf, 0, buf.length)) >= 0) {
//                System.out.println(new String(buf, 0, bytesRead));
//            }
//
//            // 关闭流，此处为示例，流关闭最好放在finally块。
//            stream.close();
//        } catch (MinioException e) {
//            System.out.println("Error occurred: " + e);
//        }
//    }
//}
