package com.leijx.newsapp.mvp.model.impl;

import android.util.Log;

import com.leijx.newsapp.bean.NewsDetailBean;
import com.leijx.newsapp.contant.AppConstant;
import com.leijx.newsapp.listener.RequestCallBack;
import com.leijx.newsapp.mvp.model.base.NewsDetailModle;
import com.leijx.newsapp.network.RetrofitManager;
import com.leijx.newsapp.util.TransformUtils;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observer;
import rx.functions.Func1;

/**
 * Created by leijx on 2017/11/5.
 */

public class NewsDetailModleImple implements NewsDetailModle<NewsDetailBean> {
    private static final String TAG = "NewsDetailModleImple";

    @Inject
    public NewsDetailModleImple(){

    }
    @Override
    public void getNewsDetail(final RequestCallBack<NewsDetailBean> requestCallBack, final String postId) {
        RetrofitManager.getInstance(AppConstant.News).getNewsDetail(postId)
                .map(new Func1<Map<String,NewsDetailBean>, NewsDetailBean>() {
                    @Override
                    public NewsDetailBean call(Map<String, NewsDetailBean> stringNewsDetailBeanMap) {
                        NewsDetailBean newsDetailBean = stringNewsDetailBeanMap.get(postId);
                        List<NewsDetailBean.ImageBean> list = newsDetailBean.getImg();
                        changeNewDetailBody(newsDetailBean, list);
                        return newsDetailBean;
                    }
                })
                .compose(TransformUtils.<NewsDetailBean>defaultSchedulers())
                .subscribe(new Observer<NewsDetailBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        requestCallBack.faile();
                    }

                    @Override
                    public void onNext(NewsDetailBean newsDetailBean) {
                        requestCallBack.success(newsDetailBean);
                    }
                });
    }

    /**
     * 将获取来的内容详情中的<!--IMG#" + i + "--> 换成对应图片的url，方便后面进行显示
     * @param newsDetailBean
     * @param list
     */
    private static void changeNewDetailBody(NewsDetailBean newsDetailBean, List<NewsDetailBean.ImageBean> list) {
        String body = newsDetailBean.getBody();
        Log.d("","oldbody=="+body);
        for(int i=0; i<list.size(); i++){
            String oldchar = "<!--IMG#" + i + "-->";
            String newchar;
            if(i==0){
                newchar = "";
            }else{
                newchar = "<img src=\""+list.get(i).getSrc()+"\" >";
            }
            body = body.replace(oldchar,newchar);
            Log.d(TAG,"replacebody=="+body);
        }
        Log.d(TAG,"newbody=="+body);
        newsDetailBean.setBody(body);
    }
}
