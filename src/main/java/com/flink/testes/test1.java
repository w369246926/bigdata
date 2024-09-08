package com.flink.testes;



import java.util.*;

public class test1 {
    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("111",1);
        map.put("111",2);
        map.put("111",3);
        map.put("222",21);
        map.put("222",22);
        map.put("222",23);
        System.out.println(map);
        if (map.get("111").equals(1)){
            map.put("111",1111);
        }else if (map.get("111").equals(2)){
            map.put("111",2222);
        }else{
            map.put("111",3333);
        }
        System.out.println(map);
        if (map.get("222").equals(21)){
            map.put("222",2323);
        }else if (map.get("222").equals(22)){
            map.put("222",2424);
        }else{
            map.put("222",2525);
        }
        System.out.println(map);


        int a = 10;


        Integer  aa = 255;








        /*long l = System.currentTimeMillis();
        System.out.println(l);
        long date = new Date().getTime();
        System.out.println(date);


        String s = IdUtil.simpleUUID();
        System.out.println(s);*/








        /*Map<String, String> map = new HashMap<String, String>();

        //添加元素
        map.put("张无忌", "赵敏");
        map.put("郭靖", "黄蓉");
        map.put("杨过", "小龙女");

        //获取所有键值对对象的集合
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        //遍历键值对对象的集合，得到每一个键值对对象
        for (Map.Entry<String, String> me : entrySet) {
            //根据键值对对象获取键和值
            String key = me.getKey();
            String value = me.getValue();
            System.out.println(key + "," + value);
        }*/
    }
}


