package com.leijx.newsapp.mvp.ui.baseviewimple;

import com.leijx.newsapp.bean.NewsChannelBean;
import com.leijx.newsapp.mvp.ui.baseview.BaseView;

import java.util.List;

/**
 * Created by leijx on 2017/11/5.
 */

public interface NewChannelView extends BaseView{
    void initRecyclerViews(List<NewsChannelBean> list_my, List<NewsChannelBean> list_more);
}
