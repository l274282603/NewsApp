package com.leijx.newsapp.mvp.ui.acyivitis;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.leijx.newsapp.R;
import com.leijx.newsapp.bean.TabEntity;
import com.leijx.newsapp.contant.AppConstant;
import com.leijx.newsapp.mvp.ui.acyivitis.base.BaseActivity;
import com.leijx.newsapp.mvp.ui.fragments.CareMainFragment;
import com.leijx.newsapp.mvp.ui.fragments.NewsMainFragment;
import com.leijx.newsapp.mvp.ui.fragments.PhotosMainFragment;
import com.leijx.newsapp.mvp.ui.fragments.VideoMainFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 首页
 * Created by leijx on 2017/10/31.
 */

public class HomePageActivity extends BaseActivity {

    @BindView(R.id.fragment_layout)
    FrameLayout fragment_layout;


    @BindView(R.id.tab_layout)
    CommonTabLayout tab_layout;

    private String[] mTitles = {"首页", "美女","视频","关注"};
    private int[] mIconUnselectIds = {
            R.mipmap.ic_home_normal,R.mipmap.ic_girl_normal,R.mipmap.ic_video_normal,R.mipmap.ic_care_normal};
    private int[] mIconSelectIds = {
            R.mipmap.ic_home_selected,R.mipmap.ic_girl_selected, R.mipmap.ic_video_selected,R.mipmap.ic_care_selected};

    private ArrayList<CustomTabEntity> tabEntityList = new ArrayList<>();
    private NewsMainFragment newsMainFragment;
    private PhotosMainFragment photosMainFragment;
    private VideoMainFragment videoMainFragment;
    private CareMainFragment careMainFragment;

    private int currentPosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment(savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_homepage;
    }

    @Override
    protected void initview() {
        for(int i = 0;i<mTitles.length;i++){
            TabEntity tabEntity = new TabEntity(mTitles[i],mIconSelectIds[i],mIconUnselectIds[i]);
            tabEntityList.add(tabEntity);
        }
        tab_layout.setTabData(tabEntityList);
        tab_layout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switchtable_fragment(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initdata() {

    }

    private void initFragment(Bundle savedInstanceState){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        if(savedInstanceState != null){
            newsMainFragment = (NewsMainFragment) getSupportFragmentManager().findFragmentByTag("NewsMainFragment");
            photosMainFragment = (PhotosMainFragment) getSupportFragmentManager().findFragmentByTag("PhotosMianFragment");
            videoMainFragment = (VideoMainFragment) getSupportFragmentManager().findFragmentByTag("VideoMainFragment");
            careMainFragment = (CareMainFragment) getSupportFragmentManager().findFragmentByTag("CareMainFragment");
            currentTabPosition = savedInstanceState.getInt(AppConstant.HOME_CURRENT_TAB_POSITION);
        }else{
            newsMainFragment = new NewsMainFragment();
            photosMainFragment = new PhotosMainFragment();
            videoMainFragment = new VideoMainFragment();
            careMainFragment = new CareMainFragment();
            fragmentTransaction.add(R.id.fragment_layout, newsMainFragment, "NewsMainFragment");
            fragmentTransaction.add(R.id.fragment_layout, photosMainFragment, "PhotosMianFragment");
            fragmentTransaction.add(R.id.fragment_layout, videoMainFragment, "VideoMainFragment");
            fragmentTransaction.add(R.id.fragment_layout, careMainFragment, "CareMainFragment");
        }
        fragmentTransaction.commit();
        switchtable_fragment(currentTabPosition);
        tab_layout.setCurrentTab(currentTabPosition);
    }


    private void switchtable_fragment(int currentTabPosition){
        currentPosition = currentTabPosition;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (currentTabPosition){
            //首页
            case 0:
                transaction.hide(photosMainFragment);
                transaction.hide(videoMainFragment);
                transaction.hide(careMainFragment);
                transaction.show(newsMainFragment);
                transaction.commitAllowingStateLoss();
                break;
            //美女
            case 1:
                transaction.hide(newsMainFragment);
                transaction.hide(videoMainFragment);
                transaction.hide(careMainFragment);
                transaction.show(photosMainFragment);
                transaction.commitAllowingStateLoss();
                break;
            //视频
            case 2:
                transaction.hide(newsMainFragment);
                transaction.hide(photosMainFragment);
                transaction.hide(careMainFragment);
                transaction.show(videoMainFragment);
                transaction.commitAllowingStateLoss();
                break;
            //关注
            case 3:
                transaction.hide(newsMainFragment);
                transaction.hide(photosMainFragment);
                transaction.hide(videoMainFragment);
                transaction.show(careMainFragment);
                transaction.commitAllowingStateLoss();
                break;
            default:
                break;
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(tab_layout != null){
            outState.putInt(AppConstant.HOME_CURRENT_TAB_POSITION,currentPosition);
        }
    }
}
