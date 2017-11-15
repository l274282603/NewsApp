package com.leijx.newsapp.mvp.ui.baseviewimple;

import com.leijx.newsapp.bean.VideoDataBean;
import com.leijx.newsapp.mvp.ui.baseview.BaseView;

import java.util.List;

/**
 * Created by leijx on 2017/11/14.
 */

public interface VideoListView extends BaseView {
    void setVideoList(List<VideoDataBean> list, int type);
}
