package io.trident.soda.security.menu;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
@Endpoint(id = "menu")
public class MenuEndpoint {

    @Resource
    private MenuManager menuManager;

    @ReadOperation
    public Map<String, AbstractMenuPriv> getMenuPriv() {
        return menuManager.getMenuPrivMap();
    }
}
