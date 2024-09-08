package com.flink.list;

import java.util.*;

public class contains {
    public static void main(String[] args) {
        //Java中的contains方法，代码如下：
        List<String> list1 = new ArrayList<>();
        if(list1.contains("element")){
            //do something
        }
        //这段代码可以判断list中是否包含"element"元素。

        Map<String, List<String>> map2 = new HashMap<>();
        List<String> list2 = new ArrayList<>();
        list2.add("element1");
        list2.add("element2");
        list2.add("element3");
        map2.put("key1", list2);

//使用Stream API过滤掉List中的元素
        map2.values().forEach(l -> l.removeIf(list2::contains));



        //Java代码实现遍历Queue<Map>，并过滤出LIST集合中的元素：


        Queue<Map> queue = new LinkedList<>();

// 遍历Queue<Map>
        while (!queue.isEmpty()) {
            Map map = queue.poll();

            // 若Map中包含LIST类型的value，则进行过滤
            for (Object value : map.values()) {
                if (value instanceof List) {
                    List list = (List) value;
                    list.removeIf(Objects::isNull); // 过滤空元素
                }
            }
        }
        //这是一个简单的Java代码示例，可以通过遍历Queue<Map>来找到LIST类型的元素，并过滤掉其中为null的元素。


//        的对应Map，最后返回过滤后的Queue<Map>。
//
//        可以使用迭代器遍历Queue<Map>中的每一个Map，再对每一个Map中的LIST集合进行遍历，判断是否包含要过滤的元素，如果包含，则从该Map中移除该元素。最后返回过滤后的Queue<Map>。
//
//        代码实现：


//        public Queue<Map> filterQueue(Queue<Map> queue, Object filterElement) {
//            Iterator<Map> iterator = queue.iterator();
//            while (iterator.hasNext()) {
//                Map map = iterator.next();
//                List list = (List) map.get("list");
//                if (list.contains(filterElement)) {
//                    list.remove(filterElement);
//                    if (list.isEmpty()) {
//                        iterator.remove();
//                    }
//                }
//            }
//            return queue;

        //项目实现
//        Queue<Map> requestQue = deviceDao.getAllDeviceIp();
//        List offDevice = FrontCommonUtils.getOffDevice();
//        Iterator<Map> iterator = requestQue.iterator();
//        while (iterator.hasNext()) {
//            Map map = iterator.next();
//            String deviceId =  map.get("deviceId").toString();
//            if (offDevice.contains(deviceId)){
//                iterator.remove();

        //其中，假设Map中使用了键名为"list"来存放LIST集合。
    }

}
