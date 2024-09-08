package com.flink.tree.test2;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private int id;
    private String name;
    private int parentId;
    private List<Category> children;

    public Category(int id, String name, int parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.children = new ArrayList<>();
    }

    public void addChild(Category child) {
        children.add(child);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getParentId() {
        return parentId;
    }

    public List<Category> getChildren() {
        return children;
    }
}
