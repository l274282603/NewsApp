package com.leijx.newsapp.mvp.ui.acyivitis;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.leijx.newsapp.App;
import com.leijx.newsapp.R;
import com.leijx.newsapp.bean.NewsDetailBean;
import com.leijx.newsapp.mvp.presenter.impl.NewsDetailPresenterImpl;
import com.leijx.newsapp.mvp.ui.acyivitis.base.BaseActivity;
import com.leijx.newsapp.mvp.ui.baseviewimple.NewsDetailView;
import com.leijx.newsapp.widget.MyImageGrtter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by leijx on 2017/11/5.
 */

public class NewsDetailActivity extends BaseActivity<NewsDetailPresenterImpl> implements NewsDetailView{

    private static final String TAG = "NewsDetailActivity";

    @BindView(R.id.newstime)
    TextView newstime_tv;
    @BindView(R.id.newsdetail)
    TextView newsdetail_tv;
    @BindView(R.id.collapsToolBarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.imageview)
    ImageView imageView;

    @Inject
    NewsDetailPresenterImpl newsDetailPresenter;

    private Context context = NewsDetailActivity.this;


    private String imgsrc = "";
    private String postid = "";
    private String newstime="";
    @Override
    protected int getLayout() {
        return R.layout.activity_newsdetail_layout;
    }

    @Override
    protected void initview() {
        collapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER); //在折叠的时候指定标题的位置
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);      //未折叠时的标题的颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);  //折叠时的标题的颜色
        newstime_tv.setText(newstime);

        Glide.with(App.getsAppContext()).load(imgsrc)
                .asBitmap()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.color.image_place_holder)
                .error(R.drawable.ic_load_fail)
                .into(imageView);



    }

    @Override
    protected void initPresenter() {
        basePresebter = newsDetailPresenter;
        basePresebter.onattachview(this);
        basePresebter.setPostId(postid);
        basePresebter.onCreat();
    }

    @Override
    protected void initInjector() {
        activityComponent.inject(this);
    }

    @Override
    protected void initdata() {
        Intent i = getIntent();
        imgsrc = i.getStringExtra("imgsrc");
        postid = i.getStringExtra("postid");
        newstime = i.getStringExtra("newstime");
    }

    @Override
    public void beforeRequest() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMsg(String message) {

    }

    @Override
    public void setNewsDetail(NewsDetailBean newsDetail) {
        //将获取来的新闻标题进行显示
        //使用CollapsingToolbarLayout时必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上不会显示。即：
        //mCollapsingToolbarLayout.setTitle(" ");
        String title = newsDetail.getTitle();
        collapsingToolbarLayout.setTitle(title);

        List<NewsDetailBean.ImageBean> imageBeanList = newsDetail.getImg();
        Log.d(TAG,"body==="+newsDetail.getBody());
        Log.d(TAG,"imageBeanList.size()==="+imageBeanList.size());
        if(imageBeanList!=null && imageBeanList.size()>1){   //内容中有图片需要加载
            MyImageGrtter myImageGrtter =new MyImageGrtter(context,newsDetail,newsdetail_tv,imageBeanList.size());
            newsdetail_tv.setText(Html.fromHtml(newsDetail.getBody(),myImageGrtter,null));

        }else{
            newsdetail_tv.setText(Html.fromHtml(newsDetail.getBody()));

        }
    }
}
