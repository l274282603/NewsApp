package com.leijx.newsapp.mvp.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.leijx.newsapp.R;
import com.leijx.newsapp.contant.AppConstant;
import com.leijx.newsapp.mvp.ui.fragments.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by leijx on 2017/10/31.
 */

public class VideoMainFragment extends BaseFragment {

    @BindView(R.id.tab_layout)
    TabLayout tab_layout;

    @BindView(R.id.viewpager_video)
    ViewPager viewPager;

    private String[] typenames = {"热点","搞笑","娱乐","精品"};
    private String[] type = {"V9LG4B3A0","V9LG4E6VR","V9LG4CHOR","00850FRB"};

    private List<VideoListFragment> fragmentList = new ArrayList<>();



    @Override
    protected int getLayout() {
        return R.layout.fragment_videomain_layout;
    }

    @Override
    protected void initview(View view) {
        fragmentList.clear();
        for(int i = 0; i < typenames.length; i++){
            VideoListFragment videoListFragment = new VideoListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(AppConstant.VideoTYPEID, type[i]);
            videoListFragment.setArguments(bundle);
            fragmentList.add(videoListFragment);
        }
        viewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public VideoListFragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return typenames[position];
            }
        });
        tab_layout.setupWithViewPager(viewPager);

    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initInjector() {
        fragmentComponent.inject(this);
    }

    @Override
    protected void initData() {

    }
}
