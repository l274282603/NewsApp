package com.leijx.newsapp.bean;

/**
 * Created by leijx on 2017/11/14.
 */

public class VideoDataBean {

    /**
     "sizeHD": 22100,
     "videoTopic": {
     "ename": "T1496245726240",
     "tname": "格调厨房",
     "alias": "每天更新美食知识",
     "topic_icons": "http://dingyue.nosdn.127.net/DMpzieMJoSP=MSy=0gcKfqDJqvcNKOHYuFwFHtM3aeMXG1496245725795.png",
     "tid": "T1496245726240"
     },
     "mp4Hd_url": "http://flv3.bn.netease.com/videolib3/1711/14/SaNlB5816/HD/SaNlB5816-mobile.mp4",
     "description": "苦瓜炒马蹄",
     "title": "苦瓜新做法，这样做出来，清脆可口，自己就能吃满满一大盘",
     "mp4_url": "http://flv3.bn.netease.com/videolib3/1711/14/SaNlB5816/SD/SaNlB5816-mobile.mp4",
     "vid": "VB28VQ3VT",
     "cover": "http://vimg3.ws.126.net/image/snapshot/2017/11/V/U/VB28VQ3VU.jpg",
     "sizeSHD": 33150,
     "playersize": 1,
     "ptime": "2017-11-14 10:17:59",
     "m3u8_url": "http://flv.bn.netease.com/videolib3/1711/14/SaNlB5816/SD/movie_index.m3u8",
     "topicImg": "http://vimg1.ws.126.net/image/snapshot/2017/6/P/0/VCLDARFP0.jpg",
     "votecount": 0,
     "length": 221,
     "videosource": "新媒体",
     "m3u8Hd_url": "http://flv.bn.netease.com/videolib3/1711/14/SaNlB5816/HD/movie_index.m3u8",
     "sizeSD": 16575,
     "topicSid": "VCLDARFOH",
     "playCount": 0,
     "replyCount": 0,
     "replyBoard": "video_bbs",
     "replyid": "B28VQ3VT050835RB",
     "topicName": "格调厨房",
     "sectiontitle": "",
     "topicDesc": "每天更新美食菜谱，通过美食营养介绍，让更多人了解食材的营养搭配，欢迎大家关注并提供视频"
     */
    private VideoTopicBean videoTopic;

    private String topicImg;
    private String videosource;
    private String mp4Hd_url;
    private String topicDesc;
    private String topicSid;
    private String cover;
    private String title;
    private int playCount;
    private String replyBoard;
    private String sectiontitle;
    private String replyid;
    private String description;
    private String mp4_url;
    private int length;
    private int playersize;
    private String m3u8Hd_url;
    private String vid;
    private String m3u8_url;
    private String ptime;
    private String topicName;

    public String getTopicImg() {
        return topicImg;
    }

    public void setTopicImg(String topicImg) {
        this.topicImg = topicImg;
    }

    public String getVideosource() {
        return videosource;
    }

    public void setVideosource(String videosource) {
        this.videosource = videosource;
    }

    public String getMp4Hd_url() {
        return mp4Hd_url;
    }

    public void setMp4Hd_url(String mp4Hd_url) {
        this.mp4Hd_url = mp4Hd_url;
    }

    public String getTopicDesc() {
        return topicDesc;
    }

    public void setTopicDesc(String topicDesc) {
        this.topicDesc = topicDesc;
    }

    public String getTopicSid() {
        return topicSid;
    }

    public void setTopicSid(String topicSid) {
        this.topicSid = topicSid;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public String getReplyBoard() {
        return replyBoard;
    }

    public void setReplyBoard(String replyBoard) {
        this.replyBoard = replyBoard;
    }

    public VideoTopicBean getVideoTopic() {
        return videoTopic;
    }

    public void setVideoTopic(VideoTopicBean videoTopic) {
        this.videoTopic = videoTopic;
    }

    public String getSectiontitle() {
        return sectiontitle;
    }

    public void setSectiontitle(String sectiontitle) {
        this.sectiontitle = sectiontitle;
    }

    public String getReplyid() {
        return replyid;
    }

    public void setReplyid(String replyid) {
        this.replyid = replyid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMp4_url() {
        return mp4_url;
    }

    public void setMp4_url(String mp4_url) {
        this.mp4_url = mp4_url;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getPlayersize() {
        return playersize;
    }

    public void setPlayersize(int playersize) {
        this.playersize = playersize;
    }

    public String getM3u8Hd_url() {
        return m3u8Hd_url;
    }

    public void setM3u8Hd_url(String m3u8Hd_url) {
        this.m3u8Hd_url = m3u8Hd_url;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getM3u8_url() {
        return m3u8_url;
    }

    public void setM3u8_url(String m3u8_url) {
        this.m3u8_url = m3u8_url;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public static class VideoTopicBean {
        private String alias;
        private String tname;
        private String ename;
        private String tid;

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public String getEname() {
            return ename;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }
    }
}
