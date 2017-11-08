package com.leijx.newsapp.mvp.ui.baseviewimple;

import com.leijx.newsapp.bean.NewsChannelBean;
import com.leijx.newsapp.mvp.ui.baseview.BaseView;

import java.util.List;

/**
 * Created by leijx on 2017/11/1.
 */

public interface NewsView extends BaseView {
    void initviewpager(List<NewsChannelBean> newsChannelBeanList);
}
