package com.leijx.newsapp.mvp.ui.baseviewimple;

import com.leijx.newsapp.bean.PhotoDataBean;
import com.leijx.newsapp.mvp.ui.baseview.BaseView;

import java.util.List;

/**
 * Created by leijx on 2017/11/7.
 */

public interface PhotoListView extends BaseView {
    void setPhotoList(List<PhotoDataBean.PhotoImageBean> list);
}
