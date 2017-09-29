package com.ggxspace.autumn.repository;

import com.ggxspace.autumn.entity.IdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * 仓库抽象接口
 * Created by ganguixiang on 2017/9/27.
 */
@NoRepositoryBean
public interface AbstractRepository<T extends IdEntity> extends JpaRepository<T, String>, AbstractRepositoryCustom<T> {
}
