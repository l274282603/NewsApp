package com.leijx.newsapp.network;

import com.leijx.newsapp.bean.NewsDetailBean;
import com.leijx.newsapp.bean.Newsbean;
import com.leijx.newsapp.bean.PhotoDataBean;
import com.leijx.newsapp.bean.VideoDataBean;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by leijx on 2017/11/2.
 */

public interface GetNewsInfoInterface {

    /**
     * 获取新闻列表
     * @param type  新闻类型
     * @param id    新闻类型ID
     * @param page  开始时的新闻数目
     * @return  返回对应的 被观察者 Observable<T> 对象
     * 当 RxJava 形式的时候，Retrofit 把请求封装进 Observable ，在请求结束后调用 onNext() 或在请求失败后调用 onError()。
     */
    @GET("/nc/article/{type}/{id}/{startpage}-20.html")
    Observable<Map<String, List<Newsbean>>> getNewslist(@Path("type") String type,
                                                        @Path("id") String id,
                                                        @Path("startpage") int page);



    /**
     * 获取新闻详情
     * @param postid
     * @return   返回对应的 被观察者 Observable<T> 对象
     * 当 RxJava 形式的时候，Retrofit 把请求封装进 Observable ，在请求结束后调用 onNext() 或在请求失败后调用 onError()。
     */
    @GET("/nc/article/{postid}/full.html")
    Observable<Map<String, NewsDetailBean>> getNewsDetail(@Path("postid") String postid);


    /**
     * 获取网络图片
     * @param url
     * @return  返回对应的 被观察者 Observable<T> 对象
     * 当 RxJava 形式的时候，Retrofit 把请求封装进 Observable ，在请求结束后调用 onNext() 或在请求失败后调用 onError()。
     */
    @GET
    Observable<ResponseBody> getimage(@Url String url);

    @GET("data/福利/{size}/{page}")
    Observable<PhotoDataBean> getPhotoList(@Path("size") int size, @Path("page") int page);

    @GET("/nc/video/list/{typeid}/n/{startpage}-10.html")
    Observable<Map<String, List<VideoDataBean>>> getVideoList(@Path("typeid") String typeid, @Path("startpage") int startpage);
}
