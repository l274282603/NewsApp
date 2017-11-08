package com.leijx.newsapp.mvp.ui.baseview;

/**
 * Created by leijx on 2017/10/31.
 */

public interface BaseView {
    void beforeRequest();

    void showProgress();

    void hideProgress();

    void showMsg(String message);

}
