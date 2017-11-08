package com.leijx.newsapp.mvp.ui.fragments.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leijx.newsapp.App;
import com.leijx.newsapp.dagger.component.DaggerFragmentComponent;
import com.leijx.newsapp.dagger.component.FragmentComponent;
import com.leijx.newsapp.dagger.module.FragmentModule;
import com.leijx.newsapp.mvp.presenter.base.BasePresenter;

import butterknife.ButterKnife;

/**
 * Fragment基类
 * Created by leijx on 2017/10/31.
 */

public abstract class BaseFragment <T extends BasePresenter> extends Fragment{

    protected FragmentComponent fragmentComponent;
    protected T presenter;
    private View mFragmentView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent = DaggerFragmentComponent.builder()
                .applicationComponent(((App)(getActivity().getApplication())).getApplicationComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
        initInjector();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        initData();
//        if(mFragmentView == null){
            mFragmentView = inflater.inflate(getLayout(), container, false);
            ViewGroup p = (ViewGroup) mFragmentView.getParent();
            if (p != null) {
                p.removeAllViewsInLayout();
            }
        ButterKnife.bind(this, mFragmentView);
            initview(mFragmentView);
//        }
        initPresenter();
        return mFragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**********************子类实现******************/
    protected abstract int getLayout();

    protected  abstract void initview(View view);

    protected abstract  void initPresenter();

    protected abstract void initInjector();

    protected abstract void initData();

    protected void startAcitivty(Context context, Class cls){
        Intent i = new Intent(context,cls);
        context.startActivity(i);

    }
}
