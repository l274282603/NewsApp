package com.leijx.newsapp.dagger.component;

import android.app.Activity;
import android.content.Context;

import com.leijx.newsapp.dagger.annotation.ContextLife;
import com.leijx.newsapp.dagger.annotation.PerFragment;
import com.leijx.newsapp.dagger.module.FragmentModule;
import com.leijx.newsapp.mvp.ui.fragments.NewsListFragment;
import com.leijx.newsapp.mvp.ui.fragments.NewsMainFragment;
import com.leijx.newsapp.mvp.ui.fragments.PhotosMainFragment;

import dagger.Component;

/**
 * Created by Eric on 2017/1/19.
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(NewsMainFragment newsMainFragment);

    void inject(NewsListFragment newsMainFragment);

    void inject(PhotosMainFragment photosMainFragment);

}
