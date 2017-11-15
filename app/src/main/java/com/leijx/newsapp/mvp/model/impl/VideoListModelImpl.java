package com.leijx.newsapp.mvp.model.impl;

import android.util.Log;

import com.leijx.newsapp.bean.VideoDataBean;
import com.leijx.newsapp.contant.AppConstant;
import com.leijx.newsapp.listener.RequestCallBack;
import com.leijx.newsapp.mvp.model.base.VideoListModle;
import com.leijx.newsapp.network.RetrofitManager;
import com.leijx.newsapp.util.TransformUtils;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by leijx on 2017/11/14.
 */

public class VideoListModelImpl implements VideoListModle<List<VideoDataBean>> {

    private static final String TAG = "VideoListModelImpl";
    @Inject
    public VideoListModelImpl(){

    }
    @Override
    public Subscription loadVideoList(final RequestCallBack<List<VideoDataBean>> requestCallBack, final String typeid, int startPage) {
        return RetrofitManager.getInstance(AppConstant.Video).getVideoList(typeid, startPage)
                .flatMap(new Func1<Map<String, List<VideoDataBean>>, Observable<VideoDataBean>>() {
                    @Override
                    public Observable<VideoDataBean> call(Map<String, List<VideoDataBean>> stringListMap) {
                        return Observable.from(stringListMap.get(typeid));
                    }
                })
                .distinct()
                .toSortedList(new Func2<VideoDataBean, VideoDataBean, Integer>() {

                    @Override
                    public Integer call(VideoDataBean videoDataBean, VideoDataBean videoDataBean2) {
                        return videoDataBean2.getPtime().compareTo(videoDataBean.getPtime());
                    }
                })
                .compose(TransformUtils.<List<VideoDataBean>>defaultSchedulers())
                .subscribe(new Observer<List<VideoDataBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError");
                        requestCallBack.faile();
                    }

                    @Override
                    public void onNext(List<VideoDataBean> data) {
                        Log.d(TAG, "data.size=="+data.size());
                        requestCallBack.success(data);
                    }
                });
    }
}
