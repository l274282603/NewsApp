package com.leijx.newsapp.mvp.model.base;

import com.leijx.newsapp.bean.NewsChannelBean;
import com.leijx.newsapp.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by leijx on 2017/11/5.
 */

public interface NewsChannelModle<T> {

    Subscription getNewsChannel(RequestCallBack<T> requestCallBack);

    void updateDb(NewsChannelBean newsChannelBean, boolean isChannleMy);

    void swapDb(NewsChannelBean fromnnewsChannelBean, NewsChannelBean tonewsChannelBean);
}
