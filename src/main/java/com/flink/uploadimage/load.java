package com.flink.uploadimage;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class load {
//    @Override
//    public String imagepath(MultipartFile file) {
//        Map<String, Object> map = new HashMap<>();
//        //获取原始图片的拓展名
//        String filename = file.getOriginalFilename();
//        //String filePath = uploadFolder + "\\" + "plan" + "\\" + System.currentTimeMillis();
//        String filePath = uploadFolder + File.separator + "plan" + File.separator + System.currentTimeMillis();
//        if (!new File(filePath).exists()) {
//            new File(filePath).mkdirs();
//        }
//        File dest = new File(filePath + File.separator + filename);
//        try {
//            file.transferTo(dest);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String imagePath = (filePath + "/" + filename).replace((uploadFolder + "\\"), "");
//        imagePath = imagePath.replace("\\", "/");
//        return imagePath;
//        //Integer row = floorDao.addFloorImageth(imagePath);
////        Integer floorId = floorDao.getFloorIdByImagePath(imagePath);
////        map.put("floorId", Integer.valueOf(floorId));
////        String url = ImageIp + ":" + ImagePort + "/Release" + "/" + imagePath;
////
////        String hostAddress = null;
////        try {
////            hostAddress = InetAddress.getLocalHost().getHostAddress();
////        } catch (UnknownHostException e) {
////            e.printStackTrace();
////        }
////
////        String ImageHostAddress = "http://" +hostAddress + ":" + ImagePort + "/Release" + "/" + imagePath;
////        if ((! ("localhost").equals(hostAddress)) && !("127.0.0.1").equals(hostAddress) ) {
////            map.put("url", ImageHostAddress);
////        }else{
////            map.put("url", url);
////        }
////        return map;
//    }
//
//    @Override
//    public String getId(Integer id) {
//
//        WarningKnowledge warningKnowledge = warningKnowledgeDao.selectById(id);
//        String image = warningKnowledge.getImage();
//
//
//        String url = ImageIp + ":" + ImagePort + "/Release" + "/" + image;
//
//        String hostAddress = null;
//        try {
//            hostAddress = InetAddress.getLocalHost().getHostAddress();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//
//        String ImageHostAddress = "http://" +hostAddress + ":" + ImagePort + "/Release" + "/" + image;
//        if ((! ("localhost").equals(hostAddress)) && !("127.0.0.1").equals(hostAddress) ) {
//            return ImageHostAddress;
//        }else{
//            return url;
//        }
//    }
}
