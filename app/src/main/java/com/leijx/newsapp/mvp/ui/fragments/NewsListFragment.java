package com.leijx.newsapp.mvp.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.leijx.newsapp.App;
import com.leijx.newsapp.R;
import com.leijx.newsapp.bean.Newsbean;
import com.leijx.newsapp.contant.AppConstant;
import com.leijx.newsapp.event.FloatingActionButtonShowEvent;
import com.leijx.newsapp.event.RxBus;
import com.leijx.newsapp.event.ScrolltoTopEvent;
import com.leijx.newsapp.mvp.presenter.impl.NewsListPresenterImpl;
import com.leijx.newsapp.mvp.ui.acyivitis.NewsDetailActivity;
import com.leijx.newsapp.mvp.ui.adapter.NewsListAdapter;
import com.leijx.newsapp.mvp.ui.baseviewimple.NewsListView;
import com.leijx.newsapp.mvp.ui.fragments.base.BaseFragment;
import com.weavey.loading.lib.LoadingLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * 新闻频道列表界面
 * Created by leijx on 2017/11/1.
 */

public class NewsListFragment extends BaseFragment<NewsListPresenterImpl> implements NewsListView{

    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @BindView(R.id.recyclerview_news)
    RecyclerView recyclerview_news;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private String channelid ;
    private String channeltype;
    @Inject
    NewsListPresenterImpl newsListPresenter;

    private NewsListAdapter newsAdapter;
    private Context context ;
    private List<Newsbean> newslist;

    private static final String TAG = "NewsListFragment";
    private boolean b = false ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        RxBus.getInstance().toObservable(ScrolltoTopEvent.class)  //监听滑动到顶层的事件
                .subscribe(new Action1<ScrolltoTopEvent>() {
                    @Override
                    public void call(ScrolltoTopEvent scrolltoTopEvent) {
                        recyclerview_news.smoothScrollToPosition(0);
                        //·目标position在第一个可见项之前 。
                        //这种情况调用smoothScrollToPosition能够平滑的滚动到指定位置，并且置顶。
                        //·目标position在第一个可见项之后，最后一个可见项之前。
                        //这种情况下，调用smoothScrollToPosition不会有任何效果···
                        //·目标position在最后一个可见项之后。
                        //这种情况调用smoothScrollToPosition会把目标项滑动到屏幕最下方···
                    }
                });
        return view;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_newslist_layout;
    }

    @Override
    protected void initview(View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview_news.setLayoutManager(linearLayoutManager);
        recyclerview_news.setItemAnimator(new DefaultItemAnimator());  //设置增删的动画

        newsAdapter = new NewsListAdapter(context);
        //由于RecyclerView并没有提供对应Item点击事件的接口，因此需要自己实现。
        //重写在 NewsAdapter中定义的OnItemsOnClicklistener接口中的onItemOnClick方法
        newsAdapter.setOnItemsOnClicklistener(new NewsListAdapter.OnItemsOnClicklistener() {
            @Override
            public void onItemOnClick(View v, int position) {
                Toast.makeText(context, "position=="+position, Toast.LENGTH_SHORT).show();
                Newsbean newsbean = newslist.get(position);
                if(newsbean.getAds()==null||newsbean.getAds().size()==0){
                    Intent i = new Intent(context, NewsDetailActivity.class);
                    i.putExtra("postid", newsbean.getPostid());
                    i.putExtra("imgsrc", newsbean.getImgsrc());
                    i.putExtra("newstime", newsbean.getPtime());
                    startActivity(i);
                }

            }
        });
        newsAdapter.setlist(newslist);
        //设置Adapter
        recyclerview_news.setAdapter(newsAdapter);
        //监听，RecyclerView的滑动
        recyclerview_news.addOnScrollListener(new RecyclerView.OnScrollListener() {  //实现上拉加载更多
            //滚动状态变化时回调  状态
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //newState：
                //RecyclerView.SCROLL_STATE_FLING; //屏幕处于甩动状态
                // RecyclerView.SCROLL_STATE_IDLE; //停止滑动状态
                // RecyclerView.SCROLL_STATE_TOUCH_SCROLL;// 手指接触状态
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                //获取到当前可见的最后一个Item对应的psition
                int lastposition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                int visiblecount = layoutManager.getChildCount();  //获取当前在页面显示出来的Item的个数，这个值不是固定的。
                int itemcount = layoutManager.getItemCount();  //获取当前总的Item个数
                if(visiblecount!=0 && newState==RecyclerView.SCROLL_STATE_IDLE
                        &&lastposition==itemcount-1){
                    newsAdapter.showloadingmore();   //更新列表，显示Item加载布局
                    presenter.loadmore();

                }

            }

            //滚动时回调 过程
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager linearLayoutManager =(LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                Log.d(TAG,"onScrolled--firstVisibleItem===="+firstVisibleItem);
                if(firstVisibleItem < 3){
                    if(b){
                        b = false;
                        RxBus.getInstance().post(new FloatingActionButtonShowEvent(false));
//                        Animation animation = AnimationUtils.loadAnimation(context,R.anim.floatingbutton_disshow_anim);
//                        homepageActivity.floatingButton.startAnimation(animation);
//                        homepageActivity.floatingButton.setVisibility(View.GONE);
//                    ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(homepageActivity.floatingButton,"scaleY",1f,0f);
//                    ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(homepageActivity.floatingButton,"scaleX",1f,0f);
//                    AnimatorSet animatorSet = new AnimatorSet();
//                    animatorSet.play(objectAnimator1).with(objectAnimator2);
//                    animatorSet.setDuration(1500);
//                    animatorSet.start();
//                    homepageActivity.floatingButton.setVisibility(View.GONE);

                    }

                }else{
                    if(b == false){
                        b = true;
                        RxBus.getInstance().post(new FloatingActionButtonShowEvent(true));
//                        homepageActivity.floatingButton.setVisibility(View.VISIBLE);
//                        Animation animation = AnimationUtils.loadAnimation(context,R.anim.floatingbutton_show_anim);
//                        homepageActivity.floatingButton.startAnimation(animation);
//                    ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(homepageActivity.floatingButton,"scaleY",0f,1f);
//                    ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(homepageActivity.floatingButton,"scaleX",0f,1f);
//                    AnimatorSet animatorSet = new AnimatorSet();
//                    animatorSet.play(objectAnimator1).with(objectAnimator2);
//                    animatorSet.setDuration(1500);
//                    animatorSet.start();

                    }

                }

            }
        });


        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);  //设置下拉刷新进度条的颜色
        //设置下拉刷新监听事件
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                newsListPresenter.onRefersh();
            }
        });
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                presenter.onRefersh();
            }
        });
    }

    @Override
    protected void initPresenter() {
        presenter = newsListPresenter;
        presenter.onattachview(this);
        presenter.setNewsIdandType(channelid, channeltype);
        presenter.onCreat();
    }

    @Override
    protected void initInjector() {
        fragmentComponent.inject(this);
    }

    @Override
    protected void initData() {
        context = App.getsAppContext();
        channelid = getArguments().getString(AppConstant.NEWCHANNEL_ID);
        channeltype = getArguments().getString(AppConstant.NEWCHANNEL_TYPE);
        Log.d(TAG,"channelid==="+channelid);
        Log.d(TAG,"channeltype==="+channeltype);
    }

    @Override
    public void beforeRequest() {
        loadingLayout.setStatus(LoadingLayout.Loading);
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
    public void setNewsList(List<Newsbean> list, int loadtype) {
        Log.d(TAG,"setNewsList---loadtype="+loadtype);
        switch (loadtype){
            case AppConstant.REFERSHNEWS_SUCESS :  //刷新成功
                if(list.size()!=0){
                    newslist = list;
                    newsAdapter.setlist(newslist);
                    newsAdapter.notifyDataSetChanged();
                    loadingLayout.setStatus(LoadingLayout.Success);
                }else{
                    loadingLayout.setStatus(LoadingLayout.Empty);
                }
                if(swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                break;
            case AppConstant.REFERSHNEWS_FAIL:     //刷新失败
                loadingLayout.setStatus(LoadingLayout.Error);
                if(swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                break;
            case AppConstant.LOADMORE_SUCESS:    //获取更多成功
//                if(list.size()!=0){
//
//                }
                newslist.addAll(list);
                newsAdapter.setlist(newslist);
                newsAdapter.hideloadingmore();  //加载更多成功后，隐藏加载布局，更新列表
                break;

            case AppConstant.LOADMORE_FAIL:      //获取更多失败
                newsAdapter.hideloadingmore();
//                Toast.makeText(context,"加载失败，请稍后重试",Toast.LENGTH_SHORT).show();
                Snackbar.make(recyclerview_news, "加载失败，请稍后重试", Snackbar.LENGTH_SHORT).show();

        }
    }
}
