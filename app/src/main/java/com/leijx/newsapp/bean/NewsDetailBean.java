package com.leijx.newsapp.bean;

import java.util.List;

/**
 * Created by leijx on 2017/11/5.
 * 新闻详情类
 */

public class NewsDetailBean {

    private String body;   //内容
    private String title;  //标题
    private List<ImageBean> img;  //图片

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ImageBean> getImg() {
        return img;
    }

    public void setImg(List<ImageBean> img) {
        this.img = img;
    }


    public class ImageBean{
        private String src;     //图片url

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }
    }
}
