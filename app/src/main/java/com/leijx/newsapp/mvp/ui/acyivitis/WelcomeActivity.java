package com.leijx.newsapp.mvp.ui.acyivitis;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.leijx.newsapp.R;
import com.leijx.newsapp.mvp.ui.acyivitis.base.BaseActivity;

import butterknife.BindView;

/**
 * 打开APP是的欢迎动画界面
 */
public class WelcomeActivity extends BaseActivity {

    private static final int[] SPLASH_ARRAY = {
            R.drawable.splash0,
            R.drawable.splash1,
            R.drawable.splash2,
            R.drawable.splash3,
            R.drawable.splash4,

            R.drawable.splash6,
            R.drawable.splash7,
            R.drawable.splash8,
            R.drawable.splash9,
            R.drawable.splash10,
            R.drawable.splash11,
            R.drawable.splash12,
            R.drawable.splash13,
            R.drawable.splash14,
            R.drawable.splash15,
            R.drawable.splash16,
    };

    @BindView(R.id.welcomeimg)
    protected ImageView welcomeimg;
    private Context context = WelcomeActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initview() {
        int randnumber=(int)(Math.random()*16);
        welcomeimg.setBackgroundResource(SPLASH_ARRAY[randnumber]);

        setanimate();
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initdata() {

    }

    /**
     * 给开机欢迎页面设置属性动画
     */
    private void setanimate() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(welcomeimg, "scaleX", 1.0f, 1.4f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(welcomeimg, "scaleY", 1.0f, 1.4f);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(3000).play(animatorX).with(animatorY);
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //动画执行完后去启动app首页
//                startActivity(HomepageActivity.class);
//                finish();
                startAcitivty(context,HomePageActivity.class);
                finish();

            }
        });
    }

}
