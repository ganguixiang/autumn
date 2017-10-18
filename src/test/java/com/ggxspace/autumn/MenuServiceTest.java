package com.ggxspace.autumn;

import com.ggxspace.autumn.entity.system.Menu;
import com.ggxspace.autumn.enums.MenuTypeEnum;
import com.ggxspace.autumn.service.system.MenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ganguixiang on 2017/10/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class MenuServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuServiceTest.class);

    @Autowired
    private MenuService menuService;

    @Test
    public void findByRoleIdsAndTypeTest() {
        List<String> roleIds = new ArrayList<>();
        roleIds.add("8a8abca65f053632015f0536b82d0000");
        List<Menu> menus = menuService.findByRoleIdsAndType(roleIds, MenuTypeEnum.MENU);
        menus.stream().forEach(m -> LOGGER.info(m.toString()));
    }
}
