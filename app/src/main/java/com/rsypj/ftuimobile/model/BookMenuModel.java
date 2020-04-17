package com.rsypj.ftuimobile.model;

/**
 * Created by Nur Hildayanti Utami on 06/02/2019.
 * feel free to contact me on nur.hildayanti.u@gmail.com
 */
public class BookMenuModel {
    public String menuName;
    public Integer id;
    public boolean hasChildren, isGroup;

    public BookMenuModel(String menuName, boolean isGroup, boolean hasChildren, Integer id) {
        this.menuName = menuName;
        this.id = id;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }
}
