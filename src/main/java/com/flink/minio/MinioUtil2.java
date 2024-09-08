package com.flink.minio;


import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

public class MinioUtil2 {
    String endpoint = "http://10.10.41.242:9292";
    String user = "minioadmin";
    String password = "minioadmin";

    public void get(String bucket) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        try {
// Create a minioClient with the MinIO server playground, its access keyand secret key.
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(endpoint)
                            .credentials(user, password)
                            .build();


// chakan
            Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucket).build());
            Iterator<Result<Item>> iterator = results.iterator();
            while (iterator.hasNext()) {
                Item item = iterator.next().get();
                String s = item.objectName();
                long size = item.size();
                System.out.println("name:" + s );
                System.out.println("size:" + size);
            }
        }catch(MinioException e){
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());
        }

    }
    public void uploader(String bucket) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        try {
// Create a minioClient with the MinIO server playground, its access keyand secret key.
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(endpoint)
                            .credentials(user, password)
                            .build();
// 创建bucket
            String bucketName = bucket;
            boolean exists =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!exists) {
// 不存在，创建bucket
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
// 上传文件
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucketName)
                            .object("微信图.png")
                            .filename("D:\\微信图.png")
                            .build());
            System.out.println("上传文件成功");


        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());
        }
    }
    public void downLoad(String bucket,String filename) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        MinioClient minioClient =
                MinioClient.builder()
                        .endpoint(endpoint)
                        .credentials(user, password)
                        .build();
// Download object given the bucket, object name and output file name
        InputStream stream2 = minioClient.getObject(
                GetObjectArgs.builder().bucket(bucket).object(filename).build());
        /*int index;
        byte[] bytes = new byte[1024];
        FileOutputStream downloadFile = new FileOutputStream("D:\\微信图222.png");
        while ((index = stream2.read(bytes)) != -1) {
            downloadFile.write(bytes, 0, index);
            downloadFile.flush();
        }
        downloadFile.close();
        stream2.close();*/
    }

}
