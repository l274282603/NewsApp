package com.leijx.newsapp.mvp.ui.fragments;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.leijx.newsapp.R;
import com.leijx.newsapp.bean.PhotoDataBean;
import com.leijx.newsapp.mvp.presenter.impl.PhotoListPresenterImpl;
import com.leijx.newsapp.mvp.ui.adapter.PhotoListAdapter;
import com.leijx.newsapp.mvp.ui.baseviewimple.PhotoListView;
import com.leijx.newsapp.mvp.ui.fragments.base.BaseFragment;

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

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

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
        iRecyclerView.setAdapter(photoListAdapter);

        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.referdata();
            }
        });
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
    public void setPhotoList(List<PhotoDataBean.PhotoImageBean> list) {
        Log.d(TAG,"setPhotoList");
        if(swipeRefresh.isRefreshing()){
            swipeRefresh.setRefreshing(false);
        }
        photoImageBeanList = list;
        photoListAdapter.setList(photoImageBeanList);
    }
}
