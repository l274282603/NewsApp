package com.leijx.newsapp.mvp.ui.baseviewimple;

import com.leijx.newsapp.bean.Newsbean;
import com.leijx.newsapp.mvp.ui.baseview.BaseView;

import java.util.List;

/**
 * Created by leijx on 2017/11/1.
 */

public interface NewsListView extends BaseView {
    void setNewsList(List<Newsbean> newsList, int loadtype);
}
