package io.trident.soda.security.menu;

import io.trident.soda.core.LoginType;

@Menu(loginType = LoginType.ALL, bizID = "Platform")
public class TestMenuPriv extends AbstractMenuPriv{

    public static final String MenuID = "Platform.Test";
    public static final String ADD = "Platform.Test.Add";

    public TestMenuPriv() {
        super(MenuID, "测试菜单", "");
        addItem(ADD, "添加");
    }

}
