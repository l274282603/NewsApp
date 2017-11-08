package com.leijx.newsapp.mvp.model.impl;

import android.util.Log;

import com.leijx.newsapp.bean.Newsbean;
import com.leijx.newsapp.contant.AppConstant;
import com.leijx.newsapp.listener.RequestCallBack;
import com.leijx.newsapp.mvp.model.base.NewsListModle;
import com.leijx.newsapp.network.RetrofitManager;
import com.leijx.newsapp.util.TransformUtils;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by leijx on 2017/11/1.
 */

public class NewsListModleImple implements NewsListModle<List<Newsbean>> {

    private static final String TAG = "NewsListModleImple";
    @Inject
    public NewsListModleImple(){

    }


    @Override
    public Subscription loadNewList(final RequestCallBack<List<Newsbean>> requestCallBack, String newstype, final String newsid, int startpage) {
        Log.d(TAG,"newsid =="+newsid+"---newstype="+newstype);
        return RetrofitManager.getInstance(AppConstant.News).getNewsList(newstype, newsid, startpage)
                .flatMap(new Func1<Map<String, List<Newsbean>>, Observable<Newsbean>>() {
                    @Override
                    public Observable<Newsbean> call(Map<String, List<Newsbean>> stringListMap) {
                        Log.d(TAG,"stringListMap.get(newsid)="+stringListMap.get(newsid));
                        if (newsid.endsWith(AppConstant.HOUSE_ID)) {
                            // 房产实际上针对地区的它的id与返回key不同
                            return Observable.from(stringListMap.get("北京"));
                        }
                        return Observable.from(stringListMap.get(newsid));
                    }
                })
                .distinct()
                .toSortedList(new Func2<Newsbean, Newsbean, Integer>() {
                    @Override
                    public Integer call(Newsbean newsbean, Newsbean newsbean2) {
                        return newsbean2.getPtime().compareTo(newsbean.getPtime());  //降序
                    }
                })
                .compose(TransformUtils.<List<Newsbean>>defaultSchedulers())
//                .subscribeOn(Schedulers.io())     //io线程中处理相关请求
//                .observeOn(AndroidSchedulers.mainThread())  //ui线程对获取来的数据进行对应处理
                .subscribe(new Observer<List<Newsbean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG,"e.Message=="+e.getMessage());
//                        Toast.makeText(App.getsAppContext(),analyzeNetworkError(e),Toast.LENGTH_LONG).show();
                        requestCallBack.faile();
                    }

                    @Override
                    public void onNext(List<Newsbean> newsbeen) {
                        requestCallBack.success(newsbeen);
                    }
                });
    }

    public  String analyzeNetworkError(Throwable e) {
        String errMsg = "";
        if (e instanceof HttpException) {
            int state = ((HttpException) e).code();
            if (state == 403) {
                errMsg = "403错误";
            }
        }
        return errMsg;
    }
}
