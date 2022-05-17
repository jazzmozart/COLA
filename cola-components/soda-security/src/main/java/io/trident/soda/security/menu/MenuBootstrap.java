package io.trident.soda.security.menu;


import com.alibaba.cola.exception.SysException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;

/**
 * ExtensionBootstrap
 *
 * @author Frank Zhang
 * @date 2020-06-18 7:55 PM
 */
@Component
public class MenuBootstrap implements ApplicationContextAware {

    @Resource
    private MenuRegister menuRegister;

    private ApplicationContext applicationContext;

    @PostConstruct
    public void init(){
        Map<String, Object> menuBeans = applicationContext.getBeansWithAnnotation(Menu.class);
        menuBeans.values().forEach(
            menu -> {
                if(menu instanceof AbstractMenuPriv){
                    menuRegister.doRegistration((AbstractMenuPriv) menu);
                } else {
                    throw new SysException("指定的菜单必须要继承AbstractMenuPriv:" + menu.getClass().getName());
                }
            }
        );
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
