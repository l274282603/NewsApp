package com.leijx.newsapp.listener;

import android.support.annotation.NonNull;

/**
 * 请求数据后的回调接口
 * Created by leijx on 2017/10/31.
 */

public interface RequestCallBack <T> {

    void success(@NonNull T data);

    void faile();
}
