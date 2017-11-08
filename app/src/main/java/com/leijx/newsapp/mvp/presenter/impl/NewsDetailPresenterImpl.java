package com.leijx.newsapp.mvp.presenter.impl;

import android.support.annotation.NonNull;

import com.leijx.newsapp.bean.NewsDetailBean;
import com.leijx.newsapp.listener.RequestCallBack;
import com.leijx.newsapp.mvp.model.base.NewsDetailModle;
import com.leijx.newsapp.mvp.model.impl.NewsDetailModleImple;
import com.leijx.newsapp.mvp.presenter.base.BasePresenter;
import com.leijx.newsapp.mvp.ui.baseview.BaseView;
import com.leijx.newsapp.mvp.ui.baseviewimple.NewsDetailView;

import javax.inject.Inject;

/**
 * Created by leijx on 2017/11/5.
 */

public class NewsDetailPresenterImpl implements BasePresenter,RequestCallBack<NewsDetailBean>{

    private NewsDetailView newsDetailView;
    private NewsDetailModle<NewsDetailBean> newsDetailModle;
    private String postId;

    @Inject
    public NewsDetailPresenterImpl(NewsDetailModleImple newsDetailModleImple){
        this.newsDetailModle = newsDetailModleImple;
    }
    @Override
    public void success(@NonNull NewsDetailBean data) {
        newsDetailView.setNewsDetail(data);
    }

    @Override
    public void faile() {

    }

    @Override
    public void onCreat() {
        newsDetailModle.getNewsDetail(this, postId);
    }

    @Override
    public void onattachview(@NonNull BaseView baseView) {
        newsDetailView = (NewsDetailView) baseView;
    }

    @Override
    public void onDestroy() {

    }

    public void setPostId(String postId){
        this.postId = postId;
    }
}
