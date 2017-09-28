package com.ggxspace.autumn.dto.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ggxspace.autumn.entity.IdEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ganguixiang on 2017/9/28.
 */
public class BaseDTO implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 描述
     */
    private String description;

    /**
     * 逻辑删除标志
     * false 未删除
     * true 删除
     */
    private Boolean delflag = Boolean.FALSE;

    public BaseDTO(IdEntity idEntity) {
        this.id = idEntity.getId();
        this.creator = idEntity.getCreator();
        this.createDate = idEntity.getCreateDate();
        this.modifier = idEntity.getModifier();
        this.modifyDate = idEntity.getModifyDate();
        this.description = idEntity.getDescription();
        this.delflag = idEntity.isDelflag();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateDate() {
        return createDate;
    }

    // 设定JSON序列化时的日期格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    // 设定JSON序列化时的日期格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDelflag() {
        return delflag;
    }

    public void setDelflag(Boolean delflag) {
        this.delflag = delflag;
    }
}
