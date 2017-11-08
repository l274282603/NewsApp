package com.leijx.newsapp.mvp.ui.baseviewimple;

import com.leijx.newsapp.bean.NewsDetailBean;
import com.leijx.newsapp.mvp.ui.baseview.BaseView;

/**
 * Created by leijx on 2017/11/5.
 */

public interface NewsDetailView extends BaseView{

    void setNewsDetail(NewsDetailBean newsDetail);
}
