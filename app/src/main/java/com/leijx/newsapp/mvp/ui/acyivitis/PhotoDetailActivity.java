package com.leijx.newsapp.mvp.ui.acyivitis;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.leijx.newsapp.R;
import com.leijx.newsapp.contant.AppConstant;
import com.leijx.newsapp.widget.PullBackLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by leijx on 2017/11/9.
 */

public class PhotoDetailActivity extends AppCompatActivity implements PullBackLayout.CallBack{

    @BindView(R.id.photoview)
    PhotoView photoView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.backgrount)
    RelativeLayout backgrount;

//    @BindView(R.id.pullBackLayout)
    PullBackLayout pullBackLayout;

    private ColorDrawable mBackground;

    String url;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photodetail_layout);
        ButterKnife.bind(this);
        initdata();
        initview();
    }

    private void initdata() {
        Intent i = getIntent();
        url = i.getStringExtra(AppConstant.PHOTOSRC);

    }

    private void initview() {
        Glide.with(this)
                .load(url)
                .asBitmap()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.color.image_place_holder)
                .error(R.drawable.ic_load_fail)
                .into(photoView);
        toolbar.setTitle("图片详情");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        photoView.setClickable(true);
        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {

            }

            @Override
            public void onOutsidePhotoTap() {

            }
        });
        pullBackLayout = (PullBackLayout) findViewById(R.id.pullBackLayout);
        Log.d("leijx","pullBackLayout=="+pullBackLayout);
        pullBackLayout.setCallBack(this);
        initBackground();
    }

    private void initBackground() {
        mBackground = new ColorDrawable(Color.BLACK);
        ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0).setBackgroundDrawable(mBackground);
    }

    @Override
    public void onPullStart() {
        toolbar.setVisibility(View.GONE);
    }

    @Override
    public void onPulling(float progress) {
        progress = Math.min(1f, progress * 3f);
        mBackground.setAlpha((int) (0xff/*255*/ * (1f - progress)));

    }

    @Override
    public void onPullComplete() {
        supportFinishAfterTransition();
    }

    @Override
    public void onPullCancel() {
        toolbar.setVisibility(View.VISIBLE);
    }
}
