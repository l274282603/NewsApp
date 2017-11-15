package com.leijx.newsapp.mvp.presenter.impl;

import android.support.annotation.NonNull;

import com.leijx.newsapp.bean.PhotoDataBean;
import com.leijx.newsapp.contant.AppConstant;
import com.leijx.newsapp.listener.RequestCallBack;
import com.leijx.newsapp.mvp.model.base.PhotoListModel;
import com.leijx.newsapp.mvp.model.impl.PhotoListModelImpl;
import com.leijx.newsapp.mvp.presenter.base.BasePresenter;
import com.leijx.newsapp.mvp.ui.baseview.BaseView;
import com.leijx.newsapp.mvp.ui.baseviewimple.PhotoListView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by leijx on 2017/11/7.
 */

public class PhotoListPresenterImpl implements BasePresenter,RequestCallBack<List<PhotoDataBean.PhotoImageBean>>{
    private PhotoListView photoListView;
    private PhotoListModel photoListModel;

    private int size = 20;
    private int page = 0;
    private boolean isRefershNews = true;

    @Inject
    public PhotoListPresenterImpl(PhotoListModelImpl photoListModel){
        this.photoListModel = photoListModel;
    }
    @Override
    public void success(@NonNull List<PhotoDataBean.PhotoImageBean> data) {
        if(data != null && data.size()>0){
            page++;
        }
        int type = isRefershNews ? AppConstant.REFERSHPHOTO_SUCESS : AppConstant.LOADMOREPHOTO_SUCESS;
        photoListView.setPhotoList(data, type);

    }

    @Override
    public void faile() {
        int type = isRefershNews ? AppConstant.REFERSHPHOTO_FAIL : AppConstant.LOADMOREPHOTO_FAIL;
        photoListView.setPhotoList(null, type);
    }

    @Override
    public void onCreat() {
        photoListModel.loadPhotoList(this,size,page);
    }

    @Override
    public void onattachview(@NonNull BaseView baseView) {
        photoListView = (PhotoListView) baseView;
    }

    @Override
    public void onDestroy() {

    }

    public void loadmore(){
        isRefershNews = false;
        photoListModel.loadPhotoList(this,size,page);
    }

    public void referdata(){
        isRefershNews = true;
        page = 0;
        photoListModel.loadPhotoList(this,size,page);
    }
}
