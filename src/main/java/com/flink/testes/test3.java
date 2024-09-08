//package com.flink.testes;
//
//import com.alibaba.fastjson.JSON;
//import org.apache.http.client.protocol.HttpClientContext;
//import org.apache.http.client.utils.HttpClientUtils;
//
//
//import java.net.InetAddress;
//import java.net.NetworkInterface;
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.Map;
//
//public class test3 {
//    public static void main(String[] args) {
////        String url="http://10.10.41.207:8181/restconf/operations/sal-strategy:set-remote-respond-host-template";
////        Map<String,Object> map =new HashMap<>();
////        Map<String,Object> input=new HashMap<>();
//        /*map.put("device_serial_id",resources.getDeviceId());
//        map.put("respond-vxlan-index",resources.getIndexId());
//        map.put("src-mac",resources.getSourceMac());
//        map.put("dst-mac",resources.getAimMac());
//        map.put("network-port",resources.getPhysicalPort());
//        map.put("src-ip",resources.getSourceIp());
//        map.put("src-port",resources.getSourcePort());
//        map.put("dest-ip",resources.getAimIp());
//        map.put("dest-port",resources.getAimPort());
//        input.put("input",map);*/
//        /*HttpClientContext httpClientContext = HttpClientUtils.addUserOAuth("admin", "admin");
//        HttpClientResult httpClientResult = HttpClientUtils.doPost(url, input, false,httpClientContext);
//        String getContent = httpClientResult.getContent();
//        Map<String,Map<String,Object>> output= JSON.parseObject(getContent,Map.class);
//        Map<String,Object>outvalue=output.get("output");
//        if(outvalue.get("code").equals(0)){
//            return busVxlanMapper.toDto(busVxlanRepository.save(resources));*/
//        char c = 65;
//        System.out.println("c = "+c);
//
//
//
//        public <T> Map<Object, List<T>> groupByProperty(List<T> list, String propertyName) {
//            Map<Object, List<T>> map = new HashMap<>();
//            for (T obj : list) {
//                try {
//                    Object value = PropertyUtils.getProperty(obj, propertyName);
//                    List<T> valueList;
//                    if (map.containsKey(value)) {
//                        valueList = map.get(value);
//                    } else {
//                        valueList = new ArrayList<>();
//                        map.put(value, valueList);
//                    }
//                    valueList.add(obj);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            return map;
//        }
//
//
//
//
//        Map<Object, List<Object>> map = new HashMap<>();
//        for(Object obj : list) {
//            Object key = getObjectKey(obj);// 获取对象的属性作为Key
//            if(map.containsKey(key)) {
//                map.get(key).add(obj);// 放入Value中
//            }else {
//                List<Object> list = new ArrayList<>();
//                list.add(obj);
//                map.put(key, list);// 放入Key-Value中
//            }
//        }
//
//
//
//
////        ConcurrentHashMap<Object, List<Object>> children = new ConcurrentHashMap<>();
////        List<BusGroupDevYs> list = new ArrayList<>();
////        Map<String, Object> map = three.stream().collect(Collectors.toMap(BusGroupDevYs::getGroupName, BusGroupDevYs::getChildren));
////        //驻地
////        String name = "";
////        //设备
////        String devnames = "";
////        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
////        while (iterator.hasNext()) {
////            Map.Entry<String, Object> entry = iterator.next();
////            //List<BusGroupDevYs> list = new CopyOnWriteArrayList<BusGroupDevYs>();
////            name = entry.getKey();
////            list = (List<BusGroupDevYs>) entry.getValue();
////        }
////        try {
////            for (BusGroupDevYs busGroupDevYs : list) {
////                String devSn = busGroupDevYs.getDevSn();
////                String pidname = busGroupDevYs.getPidname();
////                String ddname = devSn + pidname;
////                if (children.containsKey(ddname)) {
////                    children.get(ddname).add(busGroupDevYs);
////                } else {
////                    List<Object> twolist = new ArrayList<>();
////                    twolist.add(busGroupDevYs);
////                    children.put(ddname, twolist);
////                    devnames = ddname;
////                }
////            }
////        }catch (Exception e) {
////            System.out.println(e);
////        }
////        Collection<List<Object>> values = children.values();
//
////        fore.put("data_deep",2);
////        fore.put("devName",devnames);
////        fore.put("children",children);
//    }
//}
