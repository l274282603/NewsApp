package com.leijx.newsapp.dagger.component;

import android.content.Context;

import com.leijx.newsapp.dagger.annotation.ContextLife;
import com.leijx.newsapp.dagger.annotation.PerApp;
import com.leijx.newsapp.dagger.module.ApplicationModule;

import dagger.Component;

/**
 * Created by leijx on 2017/10/31.
 */
@PerApp
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ContextLife("Application")
    Context getApplication();

}
