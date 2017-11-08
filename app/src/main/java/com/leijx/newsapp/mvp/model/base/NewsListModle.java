package com.leijx.newsapp.mvp.model.base;

import com.leijx.newsapp.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by leijx on 2017/11/1.
 */

public interface NewsListModle<T> {

    Subscription loadNewList(RequestCallBack<T> requestCallBack, String newstype, String newsid, int startpage);
}
