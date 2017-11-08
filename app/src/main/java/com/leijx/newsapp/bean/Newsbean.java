package com.leijx.newsapp.bean;

import java.util.List;

/**
 * 新闻类
 * Created by leijx on 2017/11/1.
 */

public class Newsbean {

    private String postid;     //id
    private String title ;    //标题
    private String digest ;    //摘要
    private String ptime ;     //发布时间
    private String imgsrc;     //图片地址
    private List<adsBean> ads;   //图片新闻


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public List<adsBean> getAds() {
        return ads;
    }

    public void setAds(List<adsBean> ads) {
        this.ads = ads;
    }

    public class adsBean{
        private String title;  //标题
        private String imgsrc;  //图片url


        public String getTitle() {
            return title;

        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }
    }
}
