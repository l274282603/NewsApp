package com.leijx.newsapp.mvp.model.base;

import com.leijx.newsapp.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by leijx on 2017/11/7.
 */

public interface PhotoListModel<T> {
    Subscription loadPhotoList(RequestCallBack<T> requestCallBack, int size, int page);
}
