package com.sfinsight.security.menu;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MenuManager {

    public static final String MENU_TYPE_SPLIT = "#";

    public Map<String, AbstractMenuPriv> getMenuPrivMap() {
        return menuPrivMap;
    }

    private Map<String,AbstractMenuPriv> menuPrivMap = new HashMap<>();

}
