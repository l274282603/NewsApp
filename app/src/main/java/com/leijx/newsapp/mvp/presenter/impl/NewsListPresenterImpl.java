package com.leijx.newsapp.mvp.presenter.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.leijx.newsapp.bean.Newsbean;
import com.leijx.newsapp.contant.AppConstant;
import com.leijx.newsapp.listener.RequestCallBack;
import com.leijx.newsapp.mvp.model.base.NewsListModle;
import com.leijx.newsapp.mvp.model.impl.NewsListModleImple;
import com.leijx.newsapp.mvp.presenter.base.BasePresenter;
import com.leijx.newsapp.mvp.ui.baseview.BaseView;
import com.leijx.newsapp.mvp.ui.baseviewimple.NewsListView;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by leijx on 2017/11/1.
 */

public class NewsListPresenterImpl implements BasePresenter, RequestCallBack<List<Newsbean>>{

    private NewsListModle newsListModle;
    private NewsListView newsListView;

    private String newsid;
    private String newstype;
    private int startpage = 0;
    private boolean isRefershNews = true;
    private Subscription subscription;

    private static final String TAG = "NewsListPresenterImpl";

    @Inject
    public NewsListPresenterImpl(NewsListModleImple newsListModleImple){
        newsListModle = newsListModleImple;
    }
    @Override
    public void success(@NonNull List<Newsbean> data) {

        if(data != null){
            startpage = startpage+20;
        }
        int type = isRefershNews ? AppConstant.REFERSHNEWS_SUCESS : AppConstant.LOADMORE_SUCESS;
        newsListView.setNewsList(data, type);
    }

    @Override
    public void faile() {
        Log.d(TAG,"faile");
        int type = isRefershNews ? AppConstant.REFERSHNEWS_FAIL : AppConstant.LOADMORE_FAIL;
        newsListView.setNewsList(null, type);
    }

    @Override
    public void onCreat() {
        loadNewsList();
    }

    @Override
    public void onattachview(@NonNull BaseView baseView) {
        newsListView = (NewsListView) baseView;
    }

    @Override
    public void onDestroy() {
        subscription.unsubscribe();
    }

    public void setNewsIdandType(String newsid, String newstype){
        this.newsid = newsid;
        this.newstype = newstype;
    }

    private void loadNewsList(){
        subscription = newsListModle.loadNewList(this,newstype,newsid,startpage);
    }

    public void loadmore(){
        isRefershNews = false;
        loadNewsList();
    }
    public void onRefersh(){
        isRefershNews = true;
        startpage = 0;
        loadNewsList();
    }
}
