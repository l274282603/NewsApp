package com.leijx.newsapp.dagger.module;

import android.content.Context;

import com.leijx.newsapp.App;
import com.leijx.newsapp.dagger.annotation.ContextLife;
import com.leijx.newsapp.dagger.annotation.PerApp;

import dagger.Module;
import dagger.Provides;

/**
 * Created by leijx on 2017/10/31.
 */
@Module
public class ApplicationModule {
    private App app;
    public  ApplicationModule (App app){
        this.app = app;
    }

    @Provides
    @PerApp
    @ContextLife("Application")
    Context provideApplicationContext(){
        return app.getApplicationContext();
    }
}
