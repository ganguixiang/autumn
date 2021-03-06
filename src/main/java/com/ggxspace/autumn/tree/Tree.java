package com.ggxspace.autumn.tree;

import com.ggxspace.autumn.entity.TreeEntity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 树形数据
 * Created by ganguixiang on 2017/10/9.
 */
public class Tree implements Serializable {

    /**
     * id
     */
    private String id;

    /**
     * 父id
     */
    private String parentId;

    /**
     * 父名称
     */
    private String parentName;

    /**
     * 显示的文本
     */
    private String label;

    /**
     * 层级
     */
    private Integer level;

    public Tree() {
    }

    private Set<Tree> children = new HashSet<>();

    public Tree(TreeEntity treeEntity) {
        this.id = treeEntity.getId();
        this.parentId = treeEntity.getParentId();
        this.parentName = treeEntity.getParentName();
        this.label = treeEntity.getLabel();
        this.level = treeEntity.getLevel();
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<Tree> getChildren() {
        return children;
    }

    public void setChildren(Set<Tree> children) {
        this.children = children;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Tree{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", parentName='" + parentName + '\'' +
                ", label='" + label + '\'' +
                ", level=" + level +
                ", children=" + children +
                '}';
    }
}
