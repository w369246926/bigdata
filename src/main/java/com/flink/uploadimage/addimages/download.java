package com.flink.uploadimage.addimages;

import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.net.UnknownHostException;

public class download {

    @Value("${file.uploadFolder}")
    private String uploadFolder;
//    //下载图片
//    @RequestMapping(value = "/downloadImage", method = RequestMethod.GET)
//    public void KnowledgeImage(String name, HttpServletResponse response) throws UnknownHostException {
//        try {
//            //输入流，通过输入流读取文件内容
//            FileInputStream fileInputStream = new FileInputStream(new File(uploadFolder + name));
//            //输出流，通过输出流将文件写回浏览器
//            ServletOutputStream outputStream = response.getOutputStream();
//            response.setContentType("image/jpeg");
//            int len = 0;
//            byte[] bytes = new byte[1024];
//            while ((len = fileInputStream.read(bytes)) != -1){
//                outputStream.write(bytes,0,len);
//                outputStream.flush();
//            }
//            //关闭资源
//            outputStream.close();
//            fileInputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
