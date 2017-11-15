package com.leijx.newsapp.mvp.model.base;

import com.leijx.newsapp.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by leijx on 2017/11/14.
 */

public interface VideoListModle<T> {
    Subscription loadVideoList(RequestCallBack<T> requestCallBack, String typeid, int startPage);
}
