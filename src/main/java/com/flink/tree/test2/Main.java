package com.flink.tree.test2;

import com.flink.tree.test2.Category;
import com.flink.tree.test2.CategoryTree;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "电子产品", 0));
        categories.add(new Category(2, "手机", 1));
        categories.add(new Category(3, "平板电脑", 1));
        categories.add(new Category(4, "笔记本电脑", 1));
        categories.add(new Category(5, "服装鞋帽", 0));
        categories.add(new Category(6, "男装", 5));
        categories.add(new Category(7, "女装", 5));
        categories.add(new Category(8, "童装", 5));
        categories.add(new Category(9, "玩具", 0));
        categories.add(new Category(10, "益智玩具", 9));
        categories.add(new Category(11, "模型玩具", 9));
        categories.add(new Category(12, "塑料积木", 10));
        categories.add(new Category(13, "木质积木", 10));
        categories.add(new Category(14, "遥控玩具", 11));

        CategoryTree categoryTree = new CategoryTree(categories);
        categoryTree.buildTree();
        categoryTree.printTree();
    }
}
