package com.leijx.newsapp.mvp.ui.acyivitis.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.leijx.newsapp.App;
import com.leijx.newsapp.R;
import com.leijx.newsapp.dagger.component.ActivityComponent;
import com.leijx.newsapp.dagger.component.DaggerActivityComponent;
import com.leijx.newsapp.dagger.module.ActivityModule;
import com.leijx.newsapp.mvp.presenter.base.BasePresenter;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.ButterKnife;

/**
 * Activity基类
 * Created by leijx on 2017/10/31.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    protected T basePresebter;
    protected ActivityComponent activityComponent;
    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        activityComponent = DaggerActivityComponent.builder().applicationComponent(((App)getApplication()).getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
        ButterKnife.bind(this);
//        setStatusBarTranslucent();
        initdata();
        initInjector();
        initview();
        initPresenter();
    }


    @CallSuper
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(basePresebter!=null){
            basePresebter.onDestroy();
        }

    }

    @CallSuper
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**********************子类实现******************/
    /**
     * 获取布局id
     * @return
     */
    protected abstract int getLayout();

    /**
     * 初始化相应控件
     */
    protected  abstract void initview();

    /**
     * 初始化Presenter
     */
    protected abstract  void initPresenter();

    /**
     * 通过Dagger2进行依赖注入
     */
    protected abstract void initInjector();

    protected abstract void initdata();

    protected void startAcitivty(Context context, Class cls){
        Intent i = new Intent(context,cls);
        context.startActivity(i);

    }



    /**
     * 设置沉侵式状态栏
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected void setStatusBarTranslucent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorPrimary);
        }
    }
}
