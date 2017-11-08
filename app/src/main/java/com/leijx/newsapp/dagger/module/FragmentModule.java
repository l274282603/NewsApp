package com.leijx.newsapp.dagger.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.leijx.newsapp.dagger.annotation.ContextLife;
import com.leijx.newsapp.dagger.annotation.PerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Eric on 2017/1/19.
 */
@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    @PerFragment
    @ContextLife("Activity")
    public Context provideActivityContext() {
        return mFragment.getActivity();
    }

    @Provides
    @PerFragment
    public Activity provideActivity() {
        return mFragment.getActivity();
    }



    @Provides
    @PerFragment
    public Fragment provideFragment() {
        return mFragment;
    }



}
