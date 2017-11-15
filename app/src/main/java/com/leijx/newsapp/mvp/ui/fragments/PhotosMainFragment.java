package com.leijx.newsapp.mvp.ui.fragments;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.leijx.newsapp.R;
import com.leijx.newsapp.bean.PhotoDataBean;
import com.leijx.newsapp.contant.AppConstant;
import com.leijx.newsapp.mvp.presenter.impl.PhotoListPresenterImpl;
import com.leijx.newsapp.mvp.ui.acyivitis.PhotoDetailActivity;
import com.leijx.newsapp.mvp.ui.adapter.PhotoListAdapter;
import com.leijx.newsapp.mvp.ui.baseviewimple.PhotoListView;
import com.leijx.newsapp.mvp.ui.fragments.base.BaseFragment;
import com.leijx.newsapp.widget.RecyclerViewRefreshView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by leijx on 2017/10/31.
 */

public class PhotosMainFragment extends BaseFragment<PhotoListPresenterImpl> implements PhotoListView{
    private static final String TAG = "PhotosMain_leijx";

    @BindView(R.id.iRecyclerView)
    RecyclerView iRecyclerView;

    @BindView(R.id.pullToRefreshLayout)
    PullToRefreshLayout pullToRefreshLayout;

    private List<PhotoDataBean.PhotoImageBean> photoImageBeanList = new ArrayList<>();
    private PhotoListAdapter photoListAdapter;

    @Inject
    PhotoListPresenterImpl photoListPresenter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_photomain_layout;
    }

    @Override
    protected void initview(View view) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        iRecyclerView.setLayoutManager(staggeredGridLayoutManager);

        photoListAdapter = new PhotoListAdapter(getActivity(),photoImageBeanList);
        photoListAdapter.setOnItemClickListener(new PhotoListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, PhotoDataBean.PhotoImageBean photoImageBean) {
                startAcitivty(getActivity(), PhotoDetailActivity.class, photoImageBean.getUrl());
            }
        });
        iRecyclerView.setAdapter(photoListAdapter);

//        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
//        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                presenter.referdata();
//            }
//        });
        pullToRefreshLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                presenter.referdata();
            }

            @Override
            public void loadMore() {
                presenter.loadmore();
            }
        });

        pullToRefreshLayout.setHeaderView(new RecyclerViewRefreshView(getActivity()));
    }

    @Override
    protected void initPresenter() {
        presenter = photoListPresenter;
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
    public void setPhotoList(List<PhotoDataBean.PhotoImageBean> list, int type) {
        Log.d(TAG,"setPhotoList");
//        if(swipeRefresh.isRefreshing()){
//            swipeRefresh.setRefreshing(false);
//        }
        switch (type){
            case AppConstant.REFERSHPHOTO_SUCESS:
                pullToRefreshLayout.finishRefresh();
                photoImageBeanList = list;
                photoListAdapter.setList(photoImageBeanList);
                break;
            case AppConstant.REFERSHPHOTO_FAIL:
                pullToRefreshLayout.finishRefresh();
                break;
            case AppConstant.LOADMOREPHOTO_SUCESS:
                pullToRefreshLayout.finishLoadMore();
                photoImageBeanList.addAll(list);
                photoListAdapter.setList(photoImageBeanList);
                break;
            case AppConstant.LOADMOREPHOTO_FAIL:
                pullToRefreshLayout.finishLoadMore();
                break;

        }

    }
}
