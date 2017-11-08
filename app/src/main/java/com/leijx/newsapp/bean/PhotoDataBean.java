package com.leijx.newsapp.bean;

import java.util.List;

/**
 * Created by leijx on 2017/11/7.
 */

public class PhotoDataBean {
    private boolean error;
    private List<PhotoImageBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<PhotoImageBean> getResults() {
        return results;
    }

    public void setResults(List<PhotoImageBean> results) {
        this.results = results;
    }

    public class PhotoImageBean{
        /**
         *
         "_id": "59f7e677421aa90fe72535de",
         "createdAt": "2017-10-31T10:56:55.988Z",
         "desc": "10-31",
         "publishedAt": "2017-10-31T12:25:55.217Z",
         "source": "chrome",
         "type": "\u798f\u5229",
         "url": "http://7xi8d6.com1.z0.glb.clouddn.com/2017-10-31-nozomisasaki_official_31_10_2017_10_49_17_24.jpg",
         "used": true,
         "who": "\u4ee3\u7801\u5bb6"
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }
    }
}
