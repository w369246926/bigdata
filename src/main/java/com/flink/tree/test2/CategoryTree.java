package com.flink.tree.test2;

import com.flink.tree.test2.Category;

import java.util.List;

public class CategoryTree {
    private List<Category> categories;

    public CategoryTree(List<Category> categories) {
        this.categories = categories;
    }

    public void buildTree() {
        for (Category category : categories) {
            if (category.getParentId() != 0) {
                Category parentCategory = findParentCategory(category.getParentId());
                parentCategory.addChild(category);
            }
        }
    }

    private Category findParentCategory(int parentId) {
        for (Category category : categories) {
            if (category.getId() == parentId) {
                return category;
            }
        }
        return null;
    }

    public void printTree() {
        for (Category category : categories) {
            if (category.getParentId() == 0) {
                printCategory(category, 0);
            }
        }
    }

    private void printCategory(Category category, int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("-");
        }
        System.out.println(category.getName());

        for (Category child : category.getChildren()) {
            printCategory(child, level + 1);
        }
    }
}
