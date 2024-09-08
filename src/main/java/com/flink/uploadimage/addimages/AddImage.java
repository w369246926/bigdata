package com.flink.uploadimage.addimages;

import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddImage {

    @Value("${file.uploadFolder}")
    private String uploadFolder;
    //上传图片
//    @RequestMapping(value = "/addImage", method = RequestMethod.POST)
//    public Map<String, Object> addKnowledgeImage(MultipartFile file) throws UnknownHostException {
//        Map<String, Object> res = new HashMap<>();
//        String imagepath = "";
//        try {
//            imagepath = elecDefenseService.imagepath(file);
//        }catch (Exception e){
//            res.put("code", 400);
//            res.put("msg", e.getMessage());
//            return res;
//        }
////		Integer id = Knowledgeid;
////		String imageurl = warningKnowledgeService.get(id);
//        res.put("code", 200);
//        res.put("msg", "success");
//        res.put("imageurl", imagepath);
//        return res;
//    }

    //elecDefenseService.imagepath(file)

//    @Override
//    public String imagepath(MultipartFile file) {
//        Map<String, Object> map = new HashMap<>();
//        //获取原始图片的拓展名
//        String filename = file.getOriginalFilename();
//        String suffix = filename.substring(filename.lastIndexOf("."));
//
//        //使用UUID重新生成文件名，防止文件名称重复造成文件覆盖
//        filename = UUID.randomUUID().toString() + suffix;//dfsdfdfd.jpg
//
//        //创建一个目录对象
//        File dir = new File(uploadFolder);
//        //判断当前目录是否存在
//        if(!dir.exists()){
//            //目录不存在，需要创建
//            dir.mkdirs();
//        }
//
//        File dest = new File(uploadFolder +filename);
//        try {
//            file.transferTo(dest);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return filename;
//    }
}
