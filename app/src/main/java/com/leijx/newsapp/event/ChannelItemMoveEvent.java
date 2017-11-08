package com.leijx.newsapp.event;

import com.leijx.newsapp.bean.NewsChannelBean;

/**
 * Created by leijx on 2017/11/6.
 */

public class ChannelItemMoveEvent {
    private NewsChannelBean fromNewsChannelBean;
    private NewsChannelBean toNewsChannelBean;

    public NewsChannelBean getFromNewsChannelBean(){
        return fromNewsChannelBean;
    }

    public NewsChannelBean getToNewsChannelBean(){
        return toNewsChannelBean;
    }

    public ChannelItemMoveEvent(NewsChannelBean fromNewsChannelBean, NewsChannelBean toNewsChannelBean){
        this.fromNewsChannelBean = fromNewsChannelBean;
        this.toNewsChannelBean = toNewsChannelBean;
    }
}
