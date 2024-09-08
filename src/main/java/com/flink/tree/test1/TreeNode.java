package com.flink.tree.test1;

import lombok.Data;

import java.util.List;

/**
 *  TreeNode 树节点 （定义每一个节点的信息，即每一个节点对应一条数据信息）
 */
@Data
public class TreeNode {

    /** 节点ID */
    private Integer id;

    /** 父节点ID：顶级节点为0 */
    private Integer parentId;

    /** 节点名称 */
    private String label;

    /** 子节点 */
    private List<TreeNode> children;

    public TreeNode(Integer id, Integer parentId, String label) {
        this.id = id;
        this.parentId = parentId;
        this.label = label;
    }
}
