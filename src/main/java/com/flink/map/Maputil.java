package com.flink.map;

import com.flink.domain.Address;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Maputil {

//    如何将List<Map<String, Object>>类型转换成List<BusGroup>自己的泛型
//    可以使用Java 8的Stream API和Lambda表达式完成这个转换操作。
//
//    具体代码如下：

    List<Map<String, Object>> mapList = new ArrayList<>(); // 原始数据

    List<Address> result = mapList.stream()
            .map(map -> {
                Address busGroup = new Address();
                busGroup.setId(Math.toIntExact((Long) map.get("id")));
                busGroup.setCity((String) map.get("name"));
// 其他属性的转换...
                return busGroup;
            }).collect(Collectors.toList());
//
//    其中，BusGroup是自定义的实体类，包含id、name等属性。在map()操作中，
//    我们将Map<String, Object>转换成了BusGroup对象，并将其添加到结果集中。
//    最终通过collect(Collectors.toList())将结果生成List<BusGroup>类型的集合。

//    List<BusGroupDevYs> one = all.stream().filter((BusGroupDevYs) -> {
//        return BusGroupDevYs.getPid() == 0;
//    }).map((menu)->{
//        menu.setChildren(getChildrens(menu,all));
//        return menu;
//    }).collect(Collectors.toList());
//        return one;



//    private List<BusGroupDevYs> getChildrens(BusGroupDevYs root , List<BusGroupDevYs> all){
//        List<BusGroupDevYs> Children = all.stream().filter((BusGroupDevYs) -> {
//
//            return BusGroupDevYs.getPid() == root.getGroupId();
//
//        }).map((BusGroupDevYs)->{
//            BusGroupDevYs.setChildren(getChildrens(BusGroupDevYs,all));
//            return BusGroupDevYs;
//        }).collect(Collectors.toList());
//        return Children;
//    }
}
