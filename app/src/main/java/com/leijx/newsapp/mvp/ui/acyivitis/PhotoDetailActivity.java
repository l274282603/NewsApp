package com.leijx.newsapp.mvp.ui.acyivitis;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.leijx.newsapp.R;
import com.leijx.newsapp.contant.AppConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by leijx on 2017/11/9.
 */

public class PhotoDetailActivity extends AppCompatActivity {

    @BindView(R.id.photoview)
    PhotoView photoView;

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
    }
}
