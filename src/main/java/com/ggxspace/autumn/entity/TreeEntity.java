package com.ggxspace.autumn.entity;

import javax.persistence.MappedSuperclass;

/**
 * Created by ganguixiang on 2017/10/10.
 */
@MappedSuperclass
public class TreeEntity extends IdEntity {

    /**
     * 层级，方便前端显示树形结构
     */
    private Integer level;

    /**
     * 父id
     */
    private String parentId;

    /**
     * 父id列表，逗号分割
     */
    private String parentIds;

    /**
     * 上级名称
     */
    private String parentName;

    /**
     * 显示的文本
     */
    private String label;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
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

    @Override
    public String toString() {
        return "TreeEnitty{" +
                "level=" + level +
                ", parentId='" + parentId + '\'' +
                ", parentIds='" + parentIds + '\'' +
                ", parentName='" + parentName + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
