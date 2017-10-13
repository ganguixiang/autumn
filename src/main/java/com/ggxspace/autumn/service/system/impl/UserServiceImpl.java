package com.ggxspace.autumn.service.system.impl;

import com.ggxspace.autumn.entity.system.User;
import com.ggxspace.autumn.exception.AutumnException;
import com.ggxspace.autumn.repository.system.UserRepository;
import com.ggxspace.autumn.service.AbstractServiceImpl;
import com.ggxspace.autumn.service.system.UserService;
import com.ggxspace.autumn.shiro.ShiroUtils;
import com.ggxspace.autumn.util.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户service
 * Created by ganguixiang on 2017/9/27.
 */
@Service
public class UserServiceImpl extends AbstractServiceImpl<User> implements UserService {

    @Autowired
    private UserRepository userRepository;


    /**
     * 保存用户
     * @param user
     * @return
     */
    public User save(User user) {
        ObjectUtils.requireNonNull(user);
        // 校验用户名是否唯一
        User u = userRepository.findByUsername(user.getUsername());
        if (ObjectUtils.nonNull(u)) {
            throw new AutumnException("该用户名已经存在");
        }
        // 加密密码
        user.setSalt(ShiroUtils.getRandomSalt());
        user.setPassword(ShiroUtils.md5(user.getPlainPassword(), user.getSalt()));
        // 清空明文密码
        user.setPlainPassword("");

        // 保存用户
        super.save(user);

        return user;
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    public User update(User user) {
        ObjectUtils.requireNonNull(user);
        ObjectUtils.requireNonNull(user.getId());

        User u = super.get(user.getId());

        // 不允许修改用户名
        user.setUsername(u.getUsername());
        // 不允许修改密码
        user.setPassword(u.getPassword());

        super.update(user);

        return user;
    }

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * 验证用户名是否唯一
     * @param id
     * @param username
     * @return
     */
    public boolean validateUsername(String id, String username) {
        ObjectUtils.requireNonNull(username);
        List<User> users = userRepository.findByIdNotAndUsername(id, username);
        if (ObjectUtils.isNull(users)) {
            return true;
        }
        return false;
    }
}
