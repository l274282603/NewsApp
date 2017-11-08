package com.leijx.newsapp.mvp.presenter.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.leijx.newsapp.bean.NewsChannelBean;
import com.leijx.newsapp.listener.RequestCallBack;
import com.leijx.newsapp.mvp.model.base.NewsModle;
import com.leijx.newsapp.mvp.model.impl.NewsModleImpl;
import com.leijx.newsapp.mvp.presenter.base.BasePresenter;
import com.leijx.newsapp.mvp.ui.baseview.BaseView;
import com.leijx.newsapp.mvp.ui.baseviewimple.NewsView;

import java.util.List;

import javax.inject.Inject;

/**
 * 新闻模块页面对应的Presenter
 * Created by leijx on 2017/11/1.
 */

public class NewsPresenterImpl implements BasePresenter, RequestCallBack <List<NewsChannelBean>>{

    private static String TAG = "NewsPresenterImpl";
    private NewsModle<List<NewsChannelBean>> newsModle;
    private NewsView newsView;


    @Inject
    public NewsPresenterImpl(NewsModleImpl newsModle){
        this.newsModle = newsModle;
    }

    @Override
    public void onCreat() {
        newsModle.loadNewsChannel(this);
    }

    @Override
    public void onattachview(@NonNull BaseView baseView) {
        newsView = (NewsView) baseView;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void success(@NonNull List<NewsChannelBean> data) {
        Log.d(TAG,"success---data=="+data);
        if(data!=null){
            Log.d(TAG,"success---data.size=="+data.size());
        }
        newsView.initviewpager(data);
    }

    @Override
    public void faile() {

    }

    /**
     * 当数据库中的新闻频道信息发生改变的时候，在view中调用这方法去重新获取频道信息
     */
    public void onChannelDbChanged(){
        newsModle.loadNewsChannel(this);
    }


}
