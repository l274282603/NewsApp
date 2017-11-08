package com.leijx.newsapp.mvp.model.base;

import com.leijx.newsapp.listener.RequestCallBack;

/**
 * Created by leijx on 2017/11/1.
 */

public interface NewsModle<T> {

    void loadNewsChannel(RequestCallBack<T> requestCallBack);
}
