package com.ggxspace.autumn.dto.system;

import com.ggxspace.autumn.entity.IdEntity;
import com.ggxspace.autumn.entity.system.Role;
import com.ggxspace.autumn.entity.system.User;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.UsernamePasswordToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ganguixiang on 2017/10/12.
 */
public class UserDTO extends BaseDTO {

    private String username;

    private String nickname;

    private String state;

    private String roles;

    public UserDTO(User user) {
        super(user);
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.state = user.getState().getLabel();
        if (CollectionUtils.isNotEmpty(user.getRoles())) {
            StringBuilder sb = new StringBuilder();
            List<Role> roles = user.getRoles();
            for (int i = 0; i < roles.size(); i++) {
                String roleName = roles.get(i).getName();
                if (i == 0) {
                    sb.append(roleName);
                } else {
                    sb.append(",").append(roleName);
                }
            }
            this.roles = sb.toString();
        }
    }

    public static List<UserDTO> toUserDTO(List<User> users) {
        List<UserDTO> userDTOS = new ArrayList<>();
        users.stream().forEach(user -> userDTOS.add(new UserDTO(user)));
        return userDTOS;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", state='" + state + '\'' +
                ", roles='" + roles + '\'' +
                '}';
    }
}
