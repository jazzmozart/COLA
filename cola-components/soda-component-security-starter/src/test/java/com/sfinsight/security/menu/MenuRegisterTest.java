package com.sfinsight.security.menu;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class MenuRegisterTest extends TestCase {

    @Resource
    private MenuManager menuManager;

    @Test
    public void testMenuRegisted() {
        assertEquals("添加",menuManager.getMenuPrivMap().get("Platform.Test").getPrivItems().get("Platform.Test.Add"));
    }

}
