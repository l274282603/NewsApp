package com.leijx.newsapp.mvp.model.impl;

import android.util.Log;

import com.leijx.newsapp.bean.PhotoDataBean;
import com.leijx.newsapp.bean.PhotoDataBean.PhotoImageBean;
import com.leijx.newsapp.contant.AppConstant;
import com.leijx.newsapp.listener.RequestCallBack;
import com.leijx.newsapp.mvp.model.base.PhotoListModel;
import com.leijx.newsapp.network.RetrofitManager;
import com.leijx.newsapp.util.TransformUtils;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.functions.Func1;

/**
 * Created by leijx on 2017/11/7.
 */

public class PhotoListModelImpl implements PhotoListModel<List<PhotoImageBean>> {

    private static final String TAG = "PhotoListModelImpl";
    @Inject
    public PhotoListModelImpl(){

    }
    @Override
    public Subscription loadPhotoList(final RequestCallBack<List<PhotoImageBean>> requestCallBack, int size, int page) {
        return RetrofitManager.getInstance(AppConstant.Photo)
                .getPhotoList(size, page)
                .filter(new Func1<PhotoDataBean, Boolean>() {
                    @Override
                    public Boolean call(PhotoDataBean photoDataBean) {
                        Log.d(TAG,"photoDataBean.isError=="+photoDataBean.isError());
                        return !photoDataBean.isError();
                    }
                })
                .map(new Func1<PhotoDataBean, List<PhotoImageBean>>() {
                    @Override
                    public List<PhotoImageBean> call(PhotoDataBean photoDataBean) {
                        Log.d(TAG,"photoDataBean.getResults=="+photoDataBean.getResults());
                        return photoDataBean.getResults();
                    }
                })
                .compose(TransformUtils.<List<PhotoImageBean>>defaultSchedulers())
                .subscribe(new Observer<List<PhotoImageBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        requestCallBack.faile();
                    }

                    @Override
                    public void onNext(List<PhotoImageBean> photoImageBeen) {
                        requestCallBack.success(photoImageBeen);
                    }
                });
    }
}
