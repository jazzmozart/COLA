package com.sfinsight.security.menu;

import com.alibaba.cola.exception.SysException;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import javax.annotation.Resource;

import static com.sfinsight.security.menu.MenuManager.MENU_TYPE_SPLIT;

/**
 * ExtensionRegister 
 * @author fulan.zjf 2017-11-05
 */
@Component
public class MenuRegister {

    @Resource
    private MenuManager menuManager;

    public void doRegistration(AbstractMenuPriv menuPriv){
        Class<?>  menuPrivClz = menuPriv.getClass();
        if (AopUtils.isAopProxy(menuPriv)) {
            menuPrivClz = ClassUtils.getUserClass(menuPriv);
        }
        Menu menuPrivAnno = AnnotationUtils.findAnnotation(menuPrivClz, Menu.class);
        String menuId = menuPrivAnno.loginType()  + MENU_TYPE_SPLIT + menuPriv.getExtendItemID();
        if(menuManager.getMenuPrivMap().containsKey(menuId)){
            throw new SysException("存在重复的菜单定义:" + menuId);
        }
        menuManager.getMenuPrivMap().put(menuId, menuPriv);
    }

}
