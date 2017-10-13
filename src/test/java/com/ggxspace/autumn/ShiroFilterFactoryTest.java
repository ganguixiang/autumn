package com.ggxspace.autumn;

import com.ggxspace.autumn.shiro.ShiroFilterFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by ganguixiang on 2017/10/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ShiroFilterFactoryTest {

    @Autowired
    private ShiroFilterFactory shiroFilterFactory;

    @Test
    public void setFilterChainDefinitionsTest() {
        shiroFilterFactory.setFilterChainDefinitions("");
    }
}
