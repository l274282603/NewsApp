package com.leijx.newsapp.mvp.presenter.base;

import android.support.annotation.NonNull;

import com.leijx.newsapp.mvp.ui.baseview.BaseView;

/**
 * Created by leijx on 2017/10/31.
 */

public interface BasePresenter {

    void onCreat();

    void onattachview(@NonNull BaseView baseView);

    void onDestroy();
}
