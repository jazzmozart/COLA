package com.sfinsight.security.menu;

import com.sfinsghts.extend.IExtendItem;

import java.util.HashMap;
import java.util.Map;

public class AbstractMenuPriv implements IExtendItem {

    /**
     * 后台用户是否可见
     */
    private boolean forBOSSUser = false;

    /**
     *  前台用户是否可见
     */
    private boolean forMember = false;

    private String menuID;

    private String menuName;

    private Map<String, String> privItems = new HashMap<>();
    private String Memo;

    public AbstractMenuPriv(String menuID, String menuName, String memo) {
        this.Memo = memo;
        this.menuID = menuID;
        this.menuName = menuName;
    }

    public void addItem(String itemID, String name) {
        this.privItems.put(itemID, name);
    }

    public Map<String, String> getPrivItems() {
        return this.privItems;
    }

    public String getName() {
        return this.menuName;
    }

    public String getMemo() {
        return this.Memo;
    }

    @Override
    public String getExtendItemID() {
        return this.menuID;
    }

    @Override
    public String getExtendItemName() {
        return this.menuName;
    }

    public boolean isForBOSSUser() {
        return forBOSSUser;
    }

    public void setForBOSSUser(boolean forBOSSUser) {
        this.forBOSSUser = forBOSSUser;
    }

    public boolean isForMember() {
        return forMember;
    }

    public void setForMember(boolean forMember) {
        this.forMember = forMember;
    }

}
