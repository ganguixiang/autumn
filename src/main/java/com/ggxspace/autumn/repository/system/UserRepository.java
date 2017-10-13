package com.ggxspace.autumn.repository.system;

import com.ggxspace.autumn.entity.system.User;
import com.ggxspace.autumn.repository.AbstractRepository;

import java.util.List;

/**
 * 用户仓库接口
 * Created by ganguixiang on 2017/9/27.
 */
public interface UserRepository extends AbstractRepository<User>, UserRepositoryCustom {

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 根据用户名查询id不等于传入id的用户
     * @param id
     * @param username
     * @return
     */
    List<User> findByIdNotAndUsername(String id, String username);

}
