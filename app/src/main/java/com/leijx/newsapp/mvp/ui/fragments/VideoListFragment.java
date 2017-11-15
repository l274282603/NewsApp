package com.leijx.newsapp.mvp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.leijx.newsapp.R;
import com.leijx.newsapp.bean.VideoDataBean;
import com.leijx.newsapp.contant.AppConstant;
import com.leijx.newsapp.mvp.presenter.impl.VideoListPresenterImpl;
import com.leijx.newsapp.mvp.ui.adapter.VideoListAdapter;
import com.leijx.newsapp.mvp.ui.baseviewimple.VideoListView;
import com.leijx.newsapp.mvp.ui.fragments.base.BaseFragment;
import com.leijx.newsapp.widget.RecyclerViewRefreshView;
import com.weavey.loading.lib.LoadingLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by leijx on 2017/11/14.
 */

public class VideoListFragment extends BaseFragment<VideoListPresenterImpl> implements VideoListView{

    @Inject
    VideoListPresenterImpl videoListPresenter;

    @BindView(R.id.pullToRefreshLayout_video)
    PullToRefreshLayout pullToRefreshLayout;

    @BindView(R.id.loadingLayout_video)
    LoadingLayout loadingLayout;

    @BindView(R.id.recyclerview_video)
    RecyclerView recyclerView;

    private VideoListAdapter videoListAdapter;
    private List<VideoDataBean> videoDataBeanList;
    private String typeid;
    private static final String TAG = "VideoListFragment";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_videolist_layout;
    }

    @Override
    protected void initview(View view) {
        videoListAdapter = new VideoListAdapter(getActivity(), videoDataBeanList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(videoListAdapter);

        pullToRefreshLayout.setHeaderView(new RecyclerViewRefreshView(getActivity()));
        pullToRefreshLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                presenter.onrefersh();
            }

            @Override
            public void loadMore() {
                presenter.loadmore();
            }
        });
        loadingLayout.setStatus(LoadingLayout.Loading);
    }

    @Override
    protected void initPresenter() {
        presenter = videoListPresenter;
        presenter.onattachview(this);
        presenter.setTypeid(typeid);
        presenter.onCreat();
    }

    @Override
    protected void initInjector() {
        fragmentComponent.inject(this);
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        typeid = bundle.getString(AppConstant.VideoTYPEID);
        Log.d(TAG,"typeid=="+typeid);

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
    public void setVideoList(List<VideoDataBean> list, int type) {
        if(list!=null){
            Log.d(TAG,"list.size=="+list.size());
        }
        Log.d(TAG,"type=="+type);
        switch (type){
            case AppConstant.REFERSHVIDEO_SUCESS:
                videoDataBeanList = list;
                videoListAdapter.setList(videoDataBeanList);
                pullToRefreshLayout.finishRefresh();
                loadingLayout.setStatus(LoadingLayout.Success);
                break;
            case AppConstant.REFERSHVIDEO_FAIL:
                loadingLayout.setStatus(LoadingLayout.Error);
                pullToRefreshLayout.finishRefresh();
                break;
            case AppConstant.LOADMOREVIDEO_SUCESS:
                videoDataBeanList.addAll(list);
                videoListAdapter.setList(videoDataBeanList);
                pullToRefreshLayout.finishLoadMore();
                break;
            case AppConstant.LOADMOREVIDEO_FAIL:
                pullToRefreshLayout.finishLoadMore();
                break;
        }
    }
}
