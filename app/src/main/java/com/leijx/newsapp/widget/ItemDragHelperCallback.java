package com.leijx.newsapp.widget;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

/**
 * Created by leijx on 2017/11/6.
 */

public class ItemDragHelperCallback extends ItemTouchHelper.Callback {

    private static final String TAG = "ItemDragHelperCallback";
    private boolean iscanlongclick;
    private OnItemMoveListener onItemMoveListener;

    public void setOnlongclick(boolean iscanlongclick){
        Log.d(TAG,"setOnlongclick---iscanlongclick="+iscanlongclick);
        this.iscanlongclick = iscanlongclick;
    }

    public void setOnItemMoveListener(OnItemMoveListener onItemMoveListener){
        this.onItemMoveListener = onItemMoveListener;
    }
    public interface OnItemMoveListener{
        public boolean OnItemMove(int fromposition, int toposition);
    }

    /**
     * 重写getMovementFlags()方法来指定可以支持的拖放和滑动的方向。
     * 使用helperItemTouchHelper.makeMovementFlags(int, int)来构造返回的flag。
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        Log.d(TAG,"getMovementFlags");
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int swipeFlags = 0;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * 用来通知底层数据的更新
     * @param recyclerView
     * @param viewHolder  原ViewHolder
     * @param target      目标ViewHolder
     * @return
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        Log.d(TAG,"onMove");
        return onItemMoveListener.OnItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    /**
     * 要支持长按RecyclerView item进入拖动操作，
     * 你需要在isLongPressDragEnabled()方法中返回true
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        Log.d(TAG,"isLongPressDragEnabled---iscanlongclick="+iscanlongclick);
        return iscanlongclick;
    }
}
