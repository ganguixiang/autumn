package com.ggxspace.autumn.repository;


import com.ggxspace.autumn.entity.IdEntity;
import javax.transaction.Transactional;

/**
 * Created by ganguixiang on 2017/9/27.
 */
@Transactional
public interface AbstractRepositoryCustom<T extends IdEntity> {
//    List<T> findAll();
//    void logicDelete(String id);
    T update(T entity);
}
