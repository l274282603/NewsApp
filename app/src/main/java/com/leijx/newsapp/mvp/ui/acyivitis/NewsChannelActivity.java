package com.leijx.newsapp.mvp.ui.acyivitis;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.leijx.newsapp.R;
import com.leijx.newsapp.bean.NewsChannelBean;
import com.leijx.newsapp.event.ChannelItemMoveEvent;
import com.leijx.newsapp.event.RxBus;
import com.leijx.newsapp.mvp.presenter.impl.NewsChanelPresenterImpl;
import com.leijx.newsapp.mvp.ui.acyivitis.base.BaseActivity;
import com.leijx.newsapp.mvp.ui.adapter.NewsChannelAdapter;
import com.leijx.newsapp.mvp.ui.baseviewimple.NewChannelView;
import com.leijx.newsapp.widget.ItemDragHelperCallback;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by leijx on 2017/11/5.
 */

public class NewsChannelActivity extends BaseActivity<NewsChanelPresenterImpl> implements NewChannelView{

    @BindView(R.id.mychannel_recycler)
    RecyclerView mychannel_recycler;
    @BindView(R.id.morechannel_recycler)
    RecyclerView morechannel_recycler;

    @Inject
    NewsChanelPresenterImpl newsChanelPresenter;

    private Context context = NewsChannelActivity.this;
    private NewsChannelAdapter newsChannelAdapter_my;
    private NewsChannelAdapter newsChannelAdapter_more;
    List<NewsChannelBean> mychannellist;
    List<NewsChannelBean> morechannellist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxBus.getInstance().toObservable(ChannelItemMoveEvent.class).subscribe(new Action1<ChannelItemMoveEvent>() {
            @Override
            public void call(ChannelItemMoveEvent channelItemMoveEvent) {
                basePresebter.SwapNewsChannelList(channelItemMoveEvent.getFromNewsChannelBean()
                                                    , channelItemMoveEvent.getToNewsChannelBean());
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_newschannels_layout;
    }

    @Override
    protected void initview() {

    }

    @Override
    protected void initPresenter() {
        basePresebter = newsChanelPresenter;
        basePresebter.onattachview(this);
        basePresebter.onCreat();
    }

    @Override
    protected void initInjector() {
        activityComponent.inject(this);
    }

    @Override
    protected void initdata() {

    }

    @Override
    public void beforeRequest() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMsg(String message) {

    }

    @Override
    public void initRecyclerViews(List<NewsChannelBean> list_my, List<NewsChannelBean> list_more) {
        mychannellist = list_my;
        morechannellist = list_more;
        initRecyclerView_my();
        initRecyclerView_more();

    }

    private void initRecyclerView_my(){
        mychannel_recycler.setLayoutManager(new GridLayoutManager(context,4, LinearLayoutManager.VERTICAL,false));
        mychannel_recycler.setItemAnimator(new DefaultItemAnimator());
        newsChannelAdapter_my = new NewsChannelAdapter(context,mychannellist);
        newsChannelAdapter_my.setOnItemClicklistener(new NewsChannelAdapter.OnItemClicklistener() {
            @Override
            public void onItemClick(View view, int position) {
                NewsChannelBean newsChannelBean = mychannellist.get(position);
                if(!newsChannelBean.isNewsChannelFixed()){
                    newsChannelAdapter_more.addItem(newsChannelAdapter_more.getItemCount(),newsChannelBean);
                    newsChannelAdapter_my.deleteItem(position);
//                    DataBaseUtil.updateNewsChannel(context,1,newsChannelBean);
                    basePresebter.updateNewsChannel(newsChannelBean,true);
//                    initdata();   //重新刷新数据
//                    newsChannelAdapter_my.setList(mychannellist);
//                    newsChannelAdapter_more.setList(morechannellist);
//                    DataBaseUtil.updateNewsChannellist_Select(context,newsChannelBean);
//                    mychannellist.remove(position);
//                    morechannellist.add(newsChannelBean);
                }

            }
        });

        mychannel_recycler.setAdapter(newsChannelAdapter_my);

        /**
         * 下面的代码，用来进行Item间的拖动排序         */
        ItemDragHelperCallback itemDragHelperCallback = new ItemDragHelperCallback(); //重写ItemTouchHelper.Callback
        newsChannelAdapter_my.setItemDragHelperCallback(itemDragHelperCallback);
        itemDragHelperCallback.setOnItemMoveListener(newsChannelAdapter_my);
        ItemTouchHelper itemTouchHelper =new ItemTouchHelper(itemDragHelperCallback);
        itemTouchHelper.attachToRecyclerView(mychannel_recycler);
    }
    private void initRecyclerView_more(){
        morechannel_recycler.setLayoutManager(new GridLayoutManager(context,4, LinearLayoutManager.VERTICAL,false));
        morechannel_recycler.setItemAnimator(new DefaultItemAnimator());
        newsChannelAdapter_more = new NewsChannelAdapter(context,morechannellist);
        newsChannelAdapter_more.setOnItemClicklistener(new NewsChannelAdapter.OnItemClicklistener() {
            @Override
            public void onItemClick(View view, int position) {
                NewsChannelBean newsChannelBean = morechannellist.get(position);
                newsChannelAdapter_my.addItem(newsChannelAdapter_my.getItemCount(),newsChannelBean);
                newsChannelAdapter_more.deleteItem(position);
//                DataBaseUtil.updateNewsChannel(context,0,newsChannelBean);
                basePresebter.updateNewsChannel(newsChannelBean,false);
//                initdata();   //重新刷新数据
//                newsChannelAdapter_my.setList(mychannellist);
//                newsChannelAdapter_more.setList(morechannellist);
//                DataBaseUtil.updateNewsChannellist_Select(context,newsChannelBean);
//                mychannellist.add(newsChannelBean);
//                morechannellist.remove(position);
            }
        });
        morechannel_recycler.setAdapter(newsChannelAdapter_more);
    }
}
