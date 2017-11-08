package com.leijx.newsapp.mvp.ui.fragments;

import android.view.View;
import android.widget.TextView;

import com.leijx.newsapp.R;
import com.leijx.newsapp.mvp.ui.fragments.base.BaseFragment;

/**
 * Created by leijx on 2017/10/31.
 */

public class VideoMainFragment extends BaseFragment {

    @Override
    protected int getLayout() {
        return R.layout.testlayout;
    }

    @Override
    protected void initview(View view) {
        TextView textView = (TextView) view.findViewById(R.id.test_text);
        textView.setText("VideoMainFragment");
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initData() {

    }
}
