package com.flink.tree;

import java.util.List;
import java.util.stream.Collectors;

public class 组织架构树形json展示 {

    public static void main(String[] args) {
        //一切皆对象原则,先创建实例化对象"重点是包含本对象的对象  最后private List<BusGroupDevYs> children;"
        //获取到全部分组表的数据
        //List<BusGroup> all = busGroupRepository.findAll();
        //将list
//    List<BusGroupDevYs> one = all.stream().filter((BusGroupDevYs) -> {
//        return BusGroupDevYs.getPid() == 0;
//    }).map((menu)->{
//        menu.setChildren(getChildrens(menu,all));
//        return menu;
//    }).collect(Collectors.toList());
//        return one;
    }
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
