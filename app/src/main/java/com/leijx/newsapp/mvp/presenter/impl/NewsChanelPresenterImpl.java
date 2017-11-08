package com.leijx.newsapp.mvp.presenter.impl;

import android.support.annotation.NonNull;

import com.leijx.newsapp.bean.NewsChannelBean;
import com.leijx.newsapp.event.ChannelChangeEvent;
import com.leijx.newsapp.event.RxBus;
import com.leijx.newsapp.listener.RequestCallBack;
import com.leijx.newsapp.mvp.model.base.NewsChannelModle;
import com.leijx.newsapp.mvp.model.impl.NewsChannelModleImpl;
import com.leijx.newsapp.mvp.presenter.base.BasePresenter;
import com.leijx.newsapp.mvp.ui.baseview.BaseView;
import com.leijx.newsapp.mvp.ui.baseviewimple.NewChannelView;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by leijx on 2017/11/5.
 */

public class NewsChanelPresenterImpl implements BasePresenter, RequestCallBack<Map<String, List<NewsChannelBean>>>{

    private NewChannelView newChannelView;
    private NewsChannelModle newsChannelModle;
    private boolean mIsChannelChanged = false;

    @Inject
    public NewsChanelPresenterImpl(NewsChannelModleImpl newsChannelModle){
        this.newsChannelModle = newsChannelModle;
    }
    @Override
    public void success(@NonNull Map<String, List<NewsChannelBean>> data) {
        newChannelView.initRecyclerViews(data.get("mychannel"), data.get("morechannel"));
    }

    @Override
    public void faile() {

    }

    @Override
    public void onCreat() {
        newsChannelModle.getNewsChannel(this);
    }

    @Override
    public void onattachview(@NonNull BaseView baseView) {
        newChannelView = (NewChannelView) baseView;
    }

    @Override
    public void onDestroy() {
        if(mIsChannelChanged){
            RxBus.getInstance().post(new ChannelChangeEvent());
        }
    }

    public void SwapNewsChannelList(NewsChannelBean fromnewsChannelBean, NewsChannelBean tonewsChannelBean){
        newsChannelModle.swapDb(fromnewsChannelBean, tonewsChannelBean);
        mIsChannelChanged = true;
    }

    public void updateNewsChannel(NewsChannelBean newsChannelBean, boolean isselectChannel){
        newsChannelModle.updateDb(newsChannelBean, isselectChannel);
        mIsChannelChanged = true;
    }
}
