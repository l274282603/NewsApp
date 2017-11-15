package com.leijx.newsapp.mvp.presenter.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.leijx.newsapp.bean.VideoDataBean;
import com.leijx.newsapp.contant.AppConstant;
import com.leijx.newsapp.listener.RequestCallBack;
import com.leijx.newsapp.mvp.model.base.VideoListModle;
import com.leijx.newsapp.mvp.model.impl.VideoListModelImpl;
import com.leijx.newsapp.mvp.presenter.base.BasePresenter;
import com.leijx.newsapp.mvp.ui.baseview.BaseView;
import com.leijx.newsapp.mvp.ui.baseviewimple.VideoListView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by leijx on 2017/11/14.
 */

public class VideoListPresenterImpl implements BasePresenter, RequestCallBack<List<VideoDataBean>>{

    private VideoListModle videoListModle;
    private VideoListView videoListView;

    private String typeid;
    private int startpage = 0;
    private boolean isrefersh = true;
    private static final String TAG = "VideoListPresenterImpl";

    @Inject
    public VideoListPresenterImpl(VideoListModelImpl videoListModle){
        this.videoListModle = videoListModle;
    }
    @Override
    public void success(@NonNull List<VideoDataBean> data) {
        if(data != null){
            startpage++;
        }
        int type = isrefersh ? AppConstant.REFERSHVIDEO_SUCESS : AppConstant.LOADMOREVIDEO_SUCESS;
        videoListView.setVideoList(data, type);

    }

    @Override
    public void faile() {
        int type = isrefersh ? AppConstant.REFERSHVIDEO_FAIL : AppConstant.LOADMOREVIDEO_FAIL;
        videoListView.setVideoList(null, type);
    }

    @Override
    public void onCreat() {
        Log.d(TAG,"onCreat");
        videoListModle.loadVideoList(this, typeid, startpage);
    }

    @Override
    public void onattachview(@NonNull BaseView baseView) {
        Log.d(TAG,"onattachview");
        videoListView = (VideoListView) baseView;

    }

    @Override
    public void onDestroy() {

    }

    public void setTypeid(String typeid){
        this.typeid = typeid;
    }
    public void onrefersh(){
        Log.d(TAG,"onrefersh");
        isrefersh = true;
        startpage = 0;
        videoListModle.loadVideoList(this, typeid, startpage);
    }

    public void loadmore(){
        Log.d(TAG,"loadmore");
        isrefersh = false;
        videoListModle.loadVideoList(this, typeid, startpage);
    }
}
