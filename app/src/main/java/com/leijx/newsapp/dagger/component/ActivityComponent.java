package com.leijx.newsapp.dagger.component;

import android.app.Activity;
import android.content.Context;

import com.leijx.newsapp.dagger.annotation.ContextLife;
import com.leijx.newsapp.dagger.annotation.PerActivity;
import com.leijx.newsapp.dagger.module.ActivityModule;
import com.leijx.newsapp.mvp.ui.acyivitis.NewsChannelActivity;
import com.leijx.newsapp.mvp.ui.acyivitis.NewsDetailActivity;

import dagger.Component;

/**
 * Created by leijx on 2017/10/31.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(NewsDetailActivity newsDetailActivity);

    void inject(NewsChannelActivity newsChannelActivity);
}
