package com.ggxspace.autumn;

import com.ggxspace.autumn.entity.system.User;
import com.ggxspace.autumn.service.system.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by ganguixiang on 2017/9/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class UserServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceTest.class);

    @Autowired
    private UserService userService;

    @Test
    public void findAllTest() {
        List<User> users = userService.findAll();
        LOGGER.info(users.toString());
    }

    @Test
    public void saveTest() {
        User user = new User();
        user.setUsername("admin");
//        userService.save(user);
    }
}
