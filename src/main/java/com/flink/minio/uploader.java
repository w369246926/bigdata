package com.flink.minio;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class uploader {
    public static void main(String[] args)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try {
// Create a minioClient with the MinIO server playground, its access keyand secret key.
                    MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint("http://10.10.41.251:9090")
                            .credentials("adminminio", "admin123456")
                            .build();
// 创建bucket
            String bucketName = "flinkstreamfilesink";
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
}
