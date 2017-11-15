package com.leijx.newsapp.network;

import com.leijx.newsapp.bean.NewsDetailBean;
import com.leijx.newsapp.bean.Newsbean;
import com.leijx.newsapp.bean.PhotoDataBean;
import com.leijx.newsapp.bean.VideoDataBean;
import com.leijx.newsapp.contant.AppConstant;

import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by leijx on 2017/11/2.
 */

public class RetrofitManager {

    private static RetrofitManager retrofitManager;
    private OkHttpClient okHttpClient;
    private GetNewsInfoInterface getNewsInfoInterface;
    private RetrofitManager(int type){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstant.getBaseUrl(type))
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        getNewsInfoInterface = retrofit.create(GetNewsInfoInterface.class);
    }


    private OkHttpClient getOkHttpClient(){
        if(okHttpClient == null){
            okHttpClient = new OkHttpClient();

        }
        return okHttpClient;
    }

    public static RetrofitManager getInstance(int type){
//        if(retrofitManager == null){
            retrofitManager = new RetrofitManager(type);
//        }
        return retrofitManager;
    }


    public Observable<Map<String, List<Newsbean>>> getNewsList(String newstype, String newsid, int startpage){
        return getNewsInfoInterface.getNewslist(newstype,newsid,startpage);
    }

    public  Observable<Map<String, NewsDetailBean>> getNewsDetail(String postId){
        return getNewsInfoInterface.getNewsDetail(postId);
    }

    public Observable<ResponseBody> getImage(String url){
        return getNewsInfoInterface.getimage(url);
    }

    public Observable<PhotoDataBean> getPhotoList(int size, int page){
        return getNewsInfoInterface.getPhotoList(size,page);
    }

    public Observable<Map<String, List<VideoDataBean>>> getVideoList(String typeid, int startpage){
        return getNewsInfoInterface.getVideoList(typeid, startpage);
    }

}
