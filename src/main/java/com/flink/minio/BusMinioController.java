//package com.flink.minio;
//
//
//import io.minio.*;
//import io.minio.messages.Item;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.compress.utils.IOUtils;
//
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//@RestController
//@Slf4j
//
//public class BusMinioController {
//    @Autowired
//    private MinioClient minioClient;
//    @Value("${minio.bucketName}")
//    private String bucketName;
//    @GetMapping("/list")
//    public List<Object> list() throws Exception {
////获取bucket列表
//        Iterable<Result<Item>> myObjects = minioClient.listObjects(
//                ListObjectsArgs.builder().bucket(bucketName).build());
//        Iterator<Result<Item>> iterator = myObjects.iterator();
//        List<Object> items = new ArrayList<>();
//        String format = "{'fileName':'%s','fileSize':'%s'}";
//        while (iterator.hasNext()) {
//            Item item = iterator.next().get();
//            //items.add(JSON.parse(String.format(format, item.objectName(),formatFileSize(item.size()))));
//        }
//        return items;
//    }
////    @PostMapping("/upload")
////    public Res upload(@RequestParam(name = "file", required = false)
////                              MultipartFile[] file) {
////        if (file == null || file.length == 0) {
////            return Res.error("上传文件不能为空");
////        }
////        List<String> orgfileNameList = new ArrayList<>(file.length);
////        for (MultipartFile multipartFile : file) {
////            String orgfileName = multipartFile.getOriginalFilename();
////            orgfileNameList.add(orgfileName);
////            try {
//////文件上传
////                InputStream in = multipartFile.getInputStream();
////                minioClient.putObject(
////                        PutObjectArgs.builder().bucket(bucketName).object(orgfileName).stream(
////                                in, multipartFile.getSize(), -1)
////                                .contentType(multipartFile.getContentType())
////                                .build());
////                in.close();
////            } catch (Exception e) {
////                log.error(e.getMessage());
////                return Res.error("上传失败");
////            }
////        }
////        Map<String, Object> data = new HashMap<String, Object>();
////        data.put("bucketName", bucketName);
////        data.put("fileName", orgfileNameList);
////        return Res.ok("上传成功",data);
////    }
//    @RequestMapping("/download/{fileName}")
//    public void download(HttpServletResponse response, @PathVariable("fileName")
//            String fileName) {
//        InputStream in = null;
//        try {
//// 获取对象信息
//            StatObjectResponse stat = minioClient.statObject(
//                    StatObjectArgs.builder().bucket(bucketName).object(fileName).build());
//            response.setContentType(stat.contentType());
//            response.setHeader("Content-Disposition", "attachment;filename=" +
//                    URLEncoder.encode(fileName, "UTF-8"));
////文件下载
//            in = minioClient.getObject(
//                    GetObjectArgs.builder()
//                            .bucket(bucketName)
//                            .object(fileName)
//                            .build());
//            IOUtils.copy(in, response.getOutputStream());
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        } finally {
//            if (in != null) {
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    log.error(e.getMessage());
//                }
//            }
//        }
//
//    }
//}
