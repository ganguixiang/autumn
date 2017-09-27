package com.ggxspace.autumn.entity.system;

import com.ggxspace.autumn.entity.IdEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户实体
 * Created by ganguixiang on 2017/9/27.
 */
@Entity
public class User extends IdEntity {

    /**
     * 用户名
     */
    @Column(unique = true)
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 明文密码，注册时需要，不保存到数据库
     */
    private String plainPassword;

    /**
     * 加密密码的盐
     */
    private String salt;

    /**
     * 用户状态
     * 使用枚举类型，数据库中只保存枚举对应的String
     */
//    @Enumerated(EnumType.STRING)
    private String state;

    /**
     * 注册日期
     */
    private Date registerDate;

    /**
     * 用户-角色关系
     * 多对多
     * 立即加载
     * 关系在user中维护，所以role是被维护方，user是维护方
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
