package com.ggxspace.autumn.service;

import com.ggxspace.autumn.entity.IdEntity;
import com.ggxspace.autumn.repository.AbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ganguixiang on 2017/9/27.
 */
public class AbstractServiceImpl<T extends IdEntity> implements AbstractService<T> {

    @Autowired
    private AbstractRepository<T> repository;

    /**
     * 根据主键获取实体
     * @param id 主键
     * @return
     */
    public T get(String id) {
        return repository.getOne(id);
    }

    /**
     * 查找全部
     * @return
     */
    public List<T> findAll() {
        return repository.findAll();
    }

    /**
     * 根据主键列表查询
     * @param ids 主键列表
     * @return
     */
    public List<T> findAll(List<String> ids) {
        return repository.findAll(ids);
    }

    /**
     * 分页查询
     * @param pageable 分页实体
     * @return
     */
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * 根据主键删除
     * @param id 主键
     */
    public void delete(String id) {
        repository.delete(id);
    }

    /**
     * 根据实体对象删除
     * @param entity 实体对象
     */
    public void delete(T entity) {
        repository.delete(entity);
    }

    /**
     * 批量删除实体对象
     * @param entities 实体对象列表
     */
    public void delete(List<T> entities) {
        repository.delete(entities);
    }

    /**
     * 保存实体对象
     * @param entity 实体对象
     * @return
     */
    public T save(T entity) {
        return repository.save(entity);
    }

    /**
     * 批量保存实体对象
     * @param entities 实体对象列表
     * @return
     */
    public List<T> save(List<T> entities) {
        return repository.save(entities);
    }

    public T update(T entity) {
        // 更新时间
        entity.setModifyDate(new Date());
        // TODO 更新人
        return repository.update(entity);
    }

    /**
     * 计数
     * @return
     */
    public long count() {
        return repository.count();
    }

    /**
     * 根据主键判断是否存在
     * @param id 主键
     * @return
     */
    public boolean exists(String id) {
        return repository.exists(id);
    }
}
