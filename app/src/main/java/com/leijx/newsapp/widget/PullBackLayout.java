package com.leijx.newsapp.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

/**
 * Created by leijx on 2017/11/9.
 */

public class PullBackLayout extends FrameLayout {

    private static final String TAG = "PullBackLayout";
    private ViewDragHelper viewDragHelper;
    private CallBack callBack;
    private final int MinimumFlingVelocity;

    public PullBackLayout( Context context) {
        this(context,null);
    }

    public PullBackLayout( Context context,  AttributeSet attrs) {
        this(context,attrs,0);
    }

    public PullBackLayout( Context context,  AttributeSet attrs,  int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        viewDragHelper = ViewDragHelper.create(this, 1f / 8f, new ViewDragHelperCallback());
        MinimumFlingVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity(); //一个最小的缩放速度
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG,"onInterceptTouchEvent");
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG,"onTouchEvent");
         viewDragHelper.processTouchEvent(event);
         return true;
    }

    @Override
    public void computeScroll() {
        Log.d(TAG,"computeScroll");
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public interface CallBack{
        void onPullStart();

        void onPulling(float progress);

        void onPullComplete();

        void onPullCancel();
    }

    public void setCallBack(CallBack callBack){
        this.callBack = callBack;
    }

    public class ViewDragHelperCallback extends ViewDragHelper.Callback {

        /**
         * 如何返回ture则表示可以捕获该view，你可以根据传入的第一个view参数决定哪些可以捕获
         * @param child
         * @param pointerId
         * @return
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            Log.d(TAG,"tryCaptureView");
            return true;
        }

        /**
         * 可以在该方法中对child移动的边界进行控制，left , top 分别为即将移动到的位置
         * @param child
         * @param left
         * @param dx
         * @return
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            Log.d(TAG,"clampViewPositionHorizontal");
            return 0;
        }

        /**
         * 可以在该方法中对child移动的边界进行控制，left , top 分别为即将移动到的位置
         * @param child
         * @param top
         * @param dy
         * @return
         */
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            Log.d(TAG,"clampViewPositionVertical");
            return Math.max(0,top);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            Log.d(TAG,"getViewHorizontalDragRange");
            return 0;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            Log.d(TAG,"getViewVerticalDragRange");
            return getHeight();
        }

        /**
         * 当view被捕获时回调
         *  @param capturedChild
         * @param activePointerId
         */
        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            Log.d(TAG,"onViewCaptured");
            if(callBack != null){
                callBack.onPullStart();
            }
        }

        /**
         * 当view的位置发生改变时回调
         * @param changedView
         * @param left
         * @param top
         * @param dx
         * @param dy
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            Log.d(TAG,"onViewPositionChanged");
            if(callBack != null){
                callBack.onPulling((float) top / (float) getHeight());
            }
        }

        /**
         * 手指释放时回调
         * @param releasedChild
         * @param xvel
         * @param yvel
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            Log.d(TAG,"onViewReleased");
            int slop = yvel>MinimumFlingVelocity ? getHeight()/6 : getHeight()/3;
            if(releasedChild.getTop() > slop){
                if(callBack != null){
                    callBack.onPullComplete();
                }
            }else{
                if(callBack != null){
                    callBack.onPullCancel();
                }
                viewDragHelper.settleCapturedViewAt(0, 0);
                invalidate();

            }


        }
    }

}
