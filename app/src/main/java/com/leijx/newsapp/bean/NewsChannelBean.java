package com.leijx.newsapp.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 新闻栏目model
 * Created by leijx on 2017/11/1.
 */
@Entity
public class NewsChannelBean {

    @Id
    @Index
    private String newsChannelname ; //对应新闻栏目内容
    @NotNull
    private String newsChannelId ;  //对应新闻栏目ID
    @NotNull
    private String newsChannelType ; //对应新闻栏目 类型
    @NotNull
    private int newsChannelIndex ;  //对应新闻栏目索引
    @NotNull
    private boolean newChannelSelect ; //对应新闻栏目是否显示
    @NotNull
    private boolean newsChannelFixed ; //对应新闻栏目是否固定

    @Generated(hash = 1701713603)
    public NewsChannelBean(String newsChannelname, @NotNull String newsChannelId,
            @NotNull String newsChannelType, int newsChannelIndex,
            boolean newChannelSelect, boolean newsChannelFixed) {
        this.newsChannelname = newsChannelname;
        this.newsChannelId = newsChannelId;
        this.newsChannelType = newsChannelType;
        this.newsChannelIndex = newsChannelIndex;
        this.newChannelSelect = newChannelSelect;
        this.newsChannelFixed = newsChannelFixed;
    }

    @Generated(hash = 654711517)
    public NewsChannelBean() {
    }

    public String getNewsChannelId() {
        return newsChannelId;
    }

    public void setNewsChannelId(String newsChannelId) {
        this.newsChannelId = newsChannelId;
    }

    public String getNewsChannelname() {
        return newsChannelname;
    }

    public void setNewsChannelname(String newsChannelname) {
        this.newsChannelname = newsChannelname;
    }

    public String getNewsChannelType() {
        return newsChannelType;
    }

    public void setNewsChannelType(String newsChannelType) {
        this.newsChannelType = newsChannelType;
    }

    public int getNewsChannelIndex() {
        return newsChannelIndex;
    }

    public void setNewsChannelIndex(int newsChannelIndex) {
        this.newsChannelIndex = newsChannelIndex;
    }

    public boolean isNewChannelSelect() {
        return newChannelSelect;
    }

    public void setNewChannelSelect(boolean newChannelSelect) {
        this.newChannelSelect = newChannelSelect;
    }

    public boolean isNewsChannelFixed() {
        return newsChannelFixed;
    }

    public void setNewsChannelFixed(boolean newsChannelFixed) {
        this.newsChannelFixed = newsChannelFixed;
    }

    public boolean getNewChannelSelect() {
        return this.newChannelSelect;
    }

    public boolean getNewsChannelFixed() {
        return this.newsChannelFixed;
    }
}
