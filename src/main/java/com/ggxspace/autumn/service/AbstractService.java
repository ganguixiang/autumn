package com.ggxspace.autumn.service;

import com.ggxspace.autumn.entity.IdEntity;
import com.ggxspace.autumn.repository.AbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by ganguixiang on 2017/9/27.
 */
//@Service
public interface AbstractService<T> {

    /**
     * 根据主键获取实体
     * @param id 主键
     * @return
     */
    public T get(String id);

    /**
     * 查找全部
     * @return
     */
    public List<T> findAll();

    /**
     * 根据主键列表查询
     * @param ids 主键列表
     * @return
     */
    public List<T> findAll(List<String> ids);

    /**
     * 分页查询
     * @param pageable 分页实体
     * @return
     */
    public Page<T> findAll(Pageable pageable);

    /**
     * 根据主键删除
     * @param id 主键
     */
    public void delete(String id);

    /**
     * 根据实体对象删除
     * @param entity 实体对象
     */
    public void delete(T entity);

    /**
     * 批量删除实体对象
     * @param entities 实体对象列表
     */
    public void delete(List<T> entities);

    /**
     * 保存实体对象
     * @param entity 实体对象
     * @return
     */
    public T save(T entity);

    /**
     * 批量保存实体对象
     * @param entities 实体对象列表
     * @return
     */
    public List<T> save(List<T> entities);

    /**
     * 计数
     * @return
     */
    public long count();

    /**
     * 根据主键判断是否存在
     * @param id 主键
     * @return
     */
    public boolean exists(String id);
}
