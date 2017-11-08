package com.leijx.newsapp.bean;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * 底层导航栏对应的bean
 */
public class TabEntity implements CustomTabEntity {
    public String title;   //标题
    public int selectedIcon;  //被选中时的图片id
    public int unSelectedIcon; //未被选中时的图片id

    public TabEntity(String title, int selectedIcon, int unSelectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }
}
