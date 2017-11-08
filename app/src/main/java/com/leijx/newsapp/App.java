package com.leijx.newsapp;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.leijx.newsapp.contant.AppConstant;
import com.leijx.newsapp.dagger.component.ApplicationComponent;
import com.leijx.newsapp.dagger.component.DaggerApplicationComponent;
import com.leijx.newsapp.dagger.module.ApplicationModule;
import com.leijx.newsapp.greendao.DaoMaster;
import com.leijx.newsapp.greendao.DaoSession;
import com.leijx.newsapp.greendao.NewsChannelBeanDao;
import com.weavey.loading.lib.LoadingLayout;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Created by leijx on 2017/10/31.
 */

public class App extends Application {

    private ApplicationComponent mApplicationComponent;
    private static DaoSession daoSession;
    private static Context sAppContext;
    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = this;
        setGreendao();
        initApplicationComponent();
        initLoadingLayout();
    }

    private void initLoadingLayout(){
        LoadingLayout.getConfig().setLoadingPageLayout(R.layout.view_loading);
    }


    void setGreendao(){
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, AppConstant.DB_NAME, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        // 在 QueryBuilder 类中内置两个 Flag 用于方便输出执行的 SQL 语句与传递参数的值
        QueryBuilder.LOG_SQL = BuildConfig.DEBUG;
        QueryBuilder.LOG_VALUES = BuildConfig.DEBUG;
    }


    private void initApplicationComponent(){
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

    }

    public static NewsChannelBeanDao getNewsChannelBeanDao(){
        return daoSession.getNewsChannelBeanDao();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public static Context getsAppContext(){
        return sAppContext;
    }
}
