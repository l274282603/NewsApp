package com.leijx.newsapp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.jwenfeng.library.pulltorefresh.view.HeadView;
import com.leijx.newsapp.R;

/**
 * Created by leijx on 2017/11/8.
 */

public class RecyclerViewRefreshView extends RelativeLayout implements HeadView{

    private static final String TAG = "RecyclerViewRefreshView";
    public RecyclerViewRefreshView(Context context) {
        this(context,null);
    }

    public RecyclerViewRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerViewRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.view_refresh_layout,null);
        addView(view);
    }


    @Override
    public void begin() {

    }

    @Override
    public void progress(float progress, float all) {

    }

    @Override
    public void finishing(float progress, float all) {

    }

    @Override
    public void loading() {

    }

    @Override
    public void normal() {

    }

    @Override
    public View getView() {
        return this;
    }
}
