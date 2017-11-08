package com.leijx.newsapp.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.leijx.newsapp.bean.NewsDetailBean;
import com.leijx.newsapp.contant.AppConstant;
import com.leijx.newsapp.network.RetrofitManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 自定义ImageGetter对象
 * Created by leijx on 2017/9/22.
 */

public class MyImageGrtter implements Html.ImageGetter {

    private static final String TAG = "MyImageGrtter";
    private Context context;
    private NewsDetailBean newsDetailBean;
    private TextView textView;  //要显示Html的textview
    private int pictotal;   //当前news详情所有的图片总个数
    private int picccount = 0;  //当前下载的个数
    private String basefile ;
    private int mPicWidth;

    public MyImageGrtter(Context context, NewsDetailBean newsDetailBean,
                         TextView textView, int pictotal){
        Log.d(TAG,"MyImageGrtter()");
        this.context = context;
        this.newsDetailBean = newsDetailBean;
        this.textView = textView;
        mPicWidth = textView.getWidth();
        this.pictotal = pictotal;
        basefile = context.getApplicationContext().getCacheDir().getAbsolutePath();
    }
    @Override
    public Drawable getDrawable(final String source) {
        Log.d(TAG,"getDrawable");
        Drawable drawable = null;
        Log.d(TAG,"source===="+source.hashCode());
        File file = new File(basefile, source.hashCode()+"");
        //如果在本地有保存，则直接获取本地的，否则，通过网络获取，从网络获取后，同时保存进本地
        if(file.exists()){
            drawable = creatDrawablefromDisk(file);
        }else{
            creatDrawablefromNet(source);
        }
        Log.d(TAG,"drwable==="+drawable);
        return drawable;
    }

    private Drawable creatDrawablefromDisk(File file) {
        Drawable drawable = Drawable.createFromPath(file.getAbsolutePath());
        //下面这一步很重要！！！！！
        if (drawable != null) {
            int picHeight = calculatePicHeight(drawable);
            //Drawable的setBounds方法有四个参数，setBounds(int left, int top, int right, int bottom),
            // 这个四参数指的是drawable将在被绘制在canvas的哪个矩形区域内。
            drawable.setBounds(0, 0, mPicWidth, picHeight);
        }
        return drawable;
    }

    private int calculatePicHeight(Drawable drawable) {
        float imgWidth = drawable.getIntrinsicWidth();
        float imgHeight = drawable.getIntrinsicHeight();
        float rate = imgHeight / imgWidth;
        return (int) (mPicWidth * rate);
    }


    private void creatDrawablefromNet(final String source) {
        Log.d(TAG,"creatDrawablefromNet");
        RetrofitManager.getInstance(AppConstant.News).getImage(source)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ResponseBody, Boolean>() {
                    @Override
                    public Boolean call(ResponseBody responseBody) {
                        //将从网络获取来的图片保存进本地目录
                        return creatimagetodisk(responseBody, source);
                    }
                })
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        Log.d(TAG,"aBoolean=="+aBoolean+"---picccount=="+picccount);
                        picccount++;
                        if(aBoolean && picccount==pictotal-2){  //将当前新闻详情中的所有图片都保存到本地后，重新加载一次html内容
                            textView.setText(Html.fromHtml(newsDetailBean.getBody(), MyImageGrtter.this, null));
                        }

                    }
                });
    }
    private Boolean creatimagetodisk(ResponseBody responseBody, String source) {
        Log.d(TAG,"creatimagetodisk");
        File file = new File(basefile, source.hashCode()+"");
        FileOutputStream fo = null;
        InputStream fi = null;
        try {
            fo = new FileOutputStream(file);
            fi = responseBody.byteStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length=fi.read(buffer)) !=-1){
                fo.write(buffer,0,length);
            }
            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                if (fo != null){
                    fo.close();
                }
                if (fi != null){
                    fi.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
                return false;
            }

        }

    }


}
