package com.leijx.newsapp.mvp.model.impl;

import android.util.Log;

import com.leijx.newsapp.bean.NewsChannelBean;
import com.leijx.newsapp.greendao.GreendaoDBManager;
import com.leijx.newsapp.listener.RequestCallBack;
import com.leijx.newsapp.mvp.model.base.NewsModle;
import com.leijx.newsapp.util.TransformUtils;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by leijx on 2017/11/1.
 */

public class NewsModleImpl implements NewsModle<List<NewsChannelBean>> {

    private static String TAG = "NewsModleImpl";
    @Inject
    public NewsModleImpl(){

    }

    @Override
    public void loadNewsChannel(final RequestCallBack<List<NewsChannelBean>> requestCallBack) {
        Observable.create(new Observable.OnSubscribe<List<NewsChannelBean>>() {
            @Override
            public void call(Subscriber<? super List<NewsChannelBean>> subscriber) {
                GreendaoDBManager.initDB();  //初始化数据库
                List<NewsChannelBean> list = GreendaoDBManager.getNewsChannel_My();
                Log.d(TAG,"list.size==="+list.size());
                subscriber.onNext(list);
                subscriber.onCompleted();

            }
        })
                .compose(TransformUtils.<List<NewsChannelBean>>defaultSchedulers())
                .subscribe(new Observer<List<NewsChannelBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        requestCallBack.faile();

                    }

                    @Override
                    public void onNext(List<NewsChannelBean> newsChannelBeanList) {
                        requestCallBack.success(newsChannelBeanList);

                    }
                });

    }
}
