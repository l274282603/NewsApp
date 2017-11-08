package com.leijx.newsapp.mvp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.leijx.newsapp.R;
import com.leijx.newsapp.bean.NewsChannelBean;
import com.leijx.newsapp.contant.AppConstant;
import com.leijx.newsapp.event.ChannelChangeEvent;
import com.leijx.newsapp.event.FloatingActionButtonShowEvent;
import com.leijx.newsapp.event.RxBus;
import com.leijx.newsapp.event.ScrolltoTopEvent;
import com.leijx.newsapp.mvp.presenter.impl.NewsPresenterImpl;
import com.leijx.newsapp.mvp.ui.acyivitis.NewsChannelActivity;
import com.leijx.newsapp.mvp.ui.baseviewimple.NewsView;
import com.leijx.newsapp.mvp.ui.fragments.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * 新闻模块主页面
 * Created by leijx on 2017/10/31.
 */

public class NewsMainFragment extends BaseFragment<NewsPresenterImpl> implements NewsView{

    @BindView(R.id.tablayout)
    TabLayout tablayout;

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @BindView(R.id.floatingButton)
    FloatingActionButton floatingButton;

    private List<NewsChannelBean> newschannellist;
    private List<NewsListFragment> newsListFragmentList = new ArrayList<NewsListFragment>();

    @Inject
    NewsPresenterImpl newsPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        RxBus.getInstance().toObservable(ChannelChangeEvent.class)  //监听频道改变的事件
                .subscribe(new Action1<ChannelChangeEvent>() {
                    @Override
                    public void call(ChannelChangeEvent channelChangeEvent) {
                        newsPresenter.onChannelDbChanged();
                    }
                });
        RxBus.getInstance().toObservable(FloatingActionButtonShowEvent.class)  //监听FloatingActionButton显示状态改变的事件
                .subscribe(new Action1<FloatingActionButtonShowEvent>() {
                    @Override
                    public void call(FloatingActionButtonShowEvent floatingActionButtonShowEvent) {
                        if(floatingActionButtonShowEvent.getIsvisiable()){
                            floatingButton.setVisibility(View.VISIBLE);
                            Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.floatingbutton_show_anim);
                            floatingButton.startAnimation(animation);
                        }else{
                            Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.floatingbutton_disshow_anim);
                            floatingButton.startAnimation(animation);
                            floatingButton.setVisibility(View.GONE);
                        }
                    }
                });
        return v;
    }

    @OnClick({R.id.floatingButton, R.id.addimg})
    public void onClick(View view){
        if(view.getId() == R.id.floatingButton){
            RxBus.getInstance().post(new ScrolltoTopEvent());  //发送滑到顶层的事件
        }else if(view.getId() == R.id.addimg){
            startAcitivty(getActivity(), NewsChannelActivity.class);
        }

    }
    @Override
    protected int getLayout() {
        return R.layout.fragment_newsmain_layout;
    }

    @Override
    protected void initview(View view) {
    }

    @Override
    protected void initPresenter() {
        presenter = newsPresenter;
        presenter.onattachview(this);
        presenter.onCreat();
    }

    @Override
    protected void initInjector() {
        fragmentComponent.inject(this);
    }

    @Override
    protected void initData() {

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
    public void initviewpager(List<NewsChannelBean> newsChannelBeanList) {
        newsListFragmentList.clear();  //这块需要注意，每次重新设置viewpager时需要先清除newsListFragmentList
        this.newschannellist = newsChannelBeanList;
        for(int i=0; i<newsChannelBeanList.size(); i++){
            NewsListFragment newsListFragment = new NewsListFragment();
            NewsChannelBean newsChannelBean = newsChannelBeanList.get(i);
            Bundle bundle = new Bundle();
            Log.d("NewsMainFragment","ID=="+newsChannelBean.getNewsChannelId());
            Log.d("NewsMainFragment","type=="+newsChannelBean.getNewsChannelType());
            bundle.putString(AppConstant.NEWCHANNEL_ID,newsChannelBean.getNewsChannelId());
            bundle.putString(AppConstant.NEWCHANNEL_TYPE,newsChannelBean.getNewsChannelType());
            newsListFragment.setArguments(bundle);
            newsListFragmentList.add(newsListFragment);
        }
        viewpager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return newsListFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return newsListFragmentList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return newschannellist.get(position).getNewsChannelname();
            }

            @Override
            public long getItemId(int position) {
                if (newsListFragmentList != null) {
                    if (position < newsListFragmentList.size()) {
                        //不同的Fragment分配的HashCode不同，从而实现刷新adapter中的fragment
                        return newsListFragmentList.get(position).hashCode();
                    }
                }
                return super.getItemId(position);
            }
        });
        tablayout.setupWithViewPager(viewpager);
    }


}
