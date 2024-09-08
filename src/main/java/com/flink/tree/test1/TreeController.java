package com.flink.tree.test1;

import java.util.ArrayList;
import java.util.List;

public class TreeController {
    public static void main(String[] args) {
        // 模拟测试数据（通常为数据库的查询结果）
        List<TreeNode> treeNodeList = new ArrayList<>();
        treeNodeList.add(new TreeNode(1,0,"顶级节点A"));
        treeNodeList.add(new TreeNode(2,0,"顶级节点B"));
        treeNodeList.add(new TreeNode(3,1,"父节点是A"));
        treeNodeList.add(new TreeNode(4,2,"父节点是B"));
        treeNodeList.add(new TreeNode(5,2,"父节点是B"));
        treeNodeList.add(new TreeNode(6,3,"父节点的ID是3"));

        // 创建树形结构（数据集合作为参数）
        TreeBuild treeBuild = new TreeBuild(treeNodeList);
        // 原查询结果转换树形结构
        treeNodeList = treeBuild.buildTree();
        // AjaxResult：个人封装返回的结果体
        System.out.println(treeNodeList);

    }
}
