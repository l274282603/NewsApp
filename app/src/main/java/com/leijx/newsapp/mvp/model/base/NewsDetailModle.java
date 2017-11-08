package com.leijx.newsapp.mvp.model.base;

import com.leijx.newsapp.listener.RequestCallBack;

/**
 * Created by leijx on 2017/11/5.
 */

public interface NewsDetailModle<T> {
    void getNewsDetail(RequestCallBack<T> requestCallBack, String postId);
}
