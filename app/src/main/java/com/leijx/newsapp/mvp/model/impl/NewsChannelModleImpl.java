package com.leijx.newsapp.mvp.model.impl;


import com.leijx.newsapp.bean.NewsChannelBean;
import com.leijx.newsapp.greendao.GreendaoDBManager;
import com.leijx.newsapp.listener.RequestCallBack;
import com.leijx.newsapp.mvp.model.base.NewsChannelModle;
import com.leijx.newsapp.util.TransformUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by leijx on 2017/11/5.
 */

public class NewsChannelModleImpl implements NewsChannelModle<Map<String, List<NewsChannelBean>>> {

    @Inject
    public NewsChannelModleImpl(){

    }
    @Override
    public Subscription getNewsChannel(final RequestCallBack requestCallBack) {
        return Observable.create(new Observable.OnSubscribe<Map<String, List<NewsChannelBean>>>() {
            @Override
            public void call(Subscriber<? super Map<String, List<NewsChannelBean>>> subscriber) {
                List<NewsChannelBean> list_my = GreendaoDBManager.getNewsChannel_My();
                List<NewsChannelBean> list_more = GreendaoDBManager.getNewsChannel_More();
                Map<String, List<NewsChannelBean>> map = new HashMap<String, List<NewsChannelBean>>();
                map.put("mychannel",list_my);
                map.put("morechannel",list_more);
                subscriber.onNext(map);

            }
        })
                .compose(TransformUtils.<Map<String, List<NewsChannelBean>>>defaultSchedulers())
                .subscribe(new Observer<Map<String, List<NewsChannelBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Map<String, List<NewsChannelBean>> stringListMap) {
                        requestCallBack.success(stringListMap);
                    }
                });
    }

    @Override
    public void updateDb(NewsChannelBean newsChannel, boolean isChannleMy) {
        if(isChannleMy){  //从 我的频道 移到 更多频道。
            List<NewsChannelBean> list = GreendaoDBManager.getNewsChannel_IndexGT(newsChannel.getNewsChannelIndex());
            for(int i = 0;i<list.size();i++){
                NewsChannelBean newsChannelBean = list.get(i);
                newsChannelBean.setNewsChannelIndex(newsChannelBean.getNewsChannelIndex()-1);
                GreendaoDBManager.updateNewsChannel(newsChannelBean);
            }
            newsChannel.setNewsChannelIndex(GreendaoDBManager.getAllsize());
            newsChannel.setNewChannelSelect(false);
            GreendaoDBManager.updateNewsChannel(newsChannel);

        }else{  //从 更多频道 移到 我的频道
            List<NewsChannelBean> list = GreendaoDBManager.loadNewsChannelsIndexLtAndIsUnselect(newsChannel.getNewsChannelIndex());
            for(int i = 0;i<list.size(); i++){
                NewsChannelBean newsChannelBean = list.get(i);
                newsChannelBean.setNewsChannelIndex(newsChannelBean.getNewsChannelIndex()+1);
                GreendaoDBManager.updateNewsChannel(newsChannelBean);
            }
            int mychannelcount = GreendaoDBManager.getNewschannelSelectSize();
            newsChannel.setNewsChannelIndex(mychannelcount);
            newsChannel.setNewChannelSelect(true);
            GreendaoDBManager.updateNewsChannel(newsChannel);

        }
    }

    @Override
    public void swapDb(NewsChannelBean fromnnewsChannelBean, NewsChannelBean tonewsChannelBean) {
        int fromindex = fromnnewsChannelBean.getNewsChannelIndex();
        int toindex = tonewsChannelBean.getNewsChannelIndex();
        //如果是相邻的两个，则直接互换NewsChannelIndex，更新数据库就可以了
        if(fromindex - toindex == 1
                ||fromindex - toindex == -1){
            fromnnewsChannelBean.setNewsChannelIndex(toindex);
            tonewsChannelBean.setNewsChannelIndex(fromindex);
            GreendaoDBManager.updateNewsChannel(fromnnewsChannelBean);
            GreendaoDBManager.updateNewsChannel(tonewsChannelBean);

        }
        //如果fromindex大于toindex，则需要先获取来toindex 到fromindex-1的所有频道，将对应的NewsChannelIndex字段加1.然后将fromNewschannel的NewsChannelIndex字段修改为目标toindex
        else if(fromindex > toindex){
            List<NewsChannelBean> list =GreendaoDBManager.getNewsChannel_fromto(toindex,fromindex-1);
            for(int i = 0;i<list.size();i++){
                NewsChannelBean newsChannelBean = list.get(i);
                newsChannelBean.setNewsChannelIndex(newsChannelBean.getNewsChannelIndex()+1);
                GreendaoDBManager.updateNewsChannel(newsChannelBean);
            }
            fromnnewsChannelBean.setNewsChannelIndex(toindex);
            GreendaoDBManager.updateNewsChannel(fromnnewsChannelBean);

        }
        //如果fromindex小于toindex，则需要先获取来fromindex+1 到 toindex 的所有频道，将对应的NewsChannelIndex字段-1.然后将fromNewschannel的NewsChannelIndex字段修改为目标toindex
        else if(fromindex < toindex){
            List<NewsChannelBean> list =GreendaoDBManager.getNewsChannel_fromto(fromindex+1,toindex);
            for(int i = 0;i<list.size();i++){
                NewsChannelBean newsChannelBean = list.get(i);
                newsChannelBean.setNewsChannelIndex(newsChannelBean.getNewsChannelIndex()-1);
                GreendaoDBManager.updateNewsChannel(newsChannelBean);
            }
            fromnnewsChannelBean.setNewsChannelIndex(toindex);
            GreendaoDBManager.updateNewsChannel(fromnnewsChannelBean);

        }

    }
}
