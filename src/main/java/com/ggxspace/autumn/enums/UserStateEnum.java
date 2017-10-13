package com.ggxspace.autumn.enums;

/**
 * 菜单类型枚举
 * Created by ganguixiang on 2017/10/11.
 */
public enum UserStateEnum {
    ACTIVE("激活", 1),
    DISABLE("禁用", 2),
    ;

    private String label;
    private Integer index;

    UserStateEnum(String label, Integer index) {
        this.label = label;
        this.index = index;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public UserStateEnum valueOf(Integer i) {
        for (UserStateEnum menuTypeEnum : UserStateEnum.values()) {
            if (menuTypeEnum.equals(i)) {
                return menuTypeEnum;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "MenuTypeEnum{" +
                "label='" + label + '\'' +
                ", index=" + index +
                '}';
    }
}
