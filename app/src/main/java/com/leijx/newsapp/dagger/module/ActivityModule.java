package com.leijx.newsapp.dagger.module;

import android.app.Activity;
import android.content.Context;

import com.leijx.newsapp.dagger.annotation.ContextLife;
import com.leijx.newsapp.dagger.annotation.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by leijx on 2017/10/31.
 */
@Module
public class ActivityModule {
    Activity activity;

    public ActivityModule(Activity activity){
        this.activity = activity;
    }

    @Provides
    @ContextLife("Activity")
    @PerActivity
    public Context ProvideActivityContext() {
        return activity;
    }

    @Provides
    @PerActivity
    public Activity ProvideActivity() {
        return activity;
    }
}
