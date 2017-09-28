package com.ggxspace.autumn.repository.system.impl;

import com.ggxspace.autumn.entity.system.User;
import com.ggxspace.autumn.repository.AbstractRepositoryImpl;
import com.ggxspace.autumn.repository.system.UserRepositoryCustom;
import org.springframework.stereotype.Repository;

/**
 * 用户仓库实现
 * Created by ganguixiang on 2017/9/27.
 */
@Repository
public class UserRepositoryImpl extends AbstractRepositoryImpl<User> implements UserRepositoryCustom {

}
