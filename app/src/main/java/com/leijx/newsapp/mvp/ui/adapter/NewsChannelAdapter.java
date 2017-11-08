package com.leijx.newsapp.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leijx.newsapp.R;
import com.leijx.newsapp.bean.NewsChannelBean;
import com.leijx.newsapp.event.ChannelItemMoveEvent;
import com.leijx.newsapp.event.RxBus;
import com.leijx.newsapp.widget.ItemDragHelperCallback;

import java.util.Collections;
import java.util.List;

/**
 * Created by leijx on 2017/10/19.
 */

public class NewsChannelAdapter extends RecyclerView.Adapter implements ItemDragHelperCallback.OnItemMoveListener{

    private static final String TAG = "NewsChannelAdapter";
    private Context context;
    private List<NewsChannelBean> list;
    private OnItemClicklistener onItemClicklistener;
    private ItemDragHelperCallback itemDragHelperCallback;


    public NewsChannelAdapter(Context context, List<NewsChannelBean> list){
        this.context = context;
        this.list = list;
    }


    public void setItemDragHelperCallback(ItemDragHelperCallback itemDragHelperCallback){
        this.itemDragHelperCallback = itemDragHelperCallback;
    }

    public interface OnItemClicklistener{
        public void onItemClick(View view, int position);
    }
    public void setOnItemClicklistener(OnItemClicklistener onItemClicklistener){
        this.onItemClicklistener = onItemClicklistener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_newschannel_layout,parent,false);
        final NewsChannelItemHolder newsChannelItemHolder =new NewsChannelItemHolder(view);
        newsChannelItemHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                NewsChannelBean newsChannelBean = list.get(newsChannelItemHolder.getLayoutPosition());
                if(newsChannelBean.isNewChannelSelect()){
                    if(newsChannelBean.isNewsChannelFixed()){
                        itemDragHelperCallback.setOnlongclick(false);
                    }else{
                        itemDragHelperCallback.setOnlongclick(true);
                    }
                }
                return false;
            }
        });
        newsChannelItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClicklistener.onItemClick(v,newsChannelItemHolder.getLayoutPosition());
            }
        });
        return newsChannelItemHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        NewsChannelItemHolder newsChannelItemHolder = (NewsChannelItemHolder) holder;
        newsChannelItemHolder.textView.setText(list.get(position).getNewsChannelname());

    }

    @Override
    public int getItemCount() {
//        Log.d(TAG,"getItemCount--list.size=="+list.size());
        if(list == null){
            return 0;
        }else{
            return list.size();
        }

    }
    public void setList(List<NewsChannelBean> list){
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * 实现ItemDragHelperCallback.OnItemMoveListener接口的OnItemMove方法，去更新相关列表数据
     * @param fromposition  原索引
     * @param toposition   目标索引
     * @return
     */
    @Override
    public boolean OnItemMove(int fromposition, int toposition) {
        if(list.get(fromposition).isNewsChannelFixed()||
                list.get(toposition).isNewsChannelFixed()){
            return false;
        }
//        DataBaseUtil.swapNewChannel(context,list.get(fromposition), list.get(toposition));
        Collections.swap(list, fromposition, toposition);
        notifyItemMoved(fromposition, toposition);
        RxBus.getInstance().post(new ChannelItemMoveEvent(list.get(fromposition), list.get(toposition)));
        return true;
    }

    /**
     * 删除对应Item
     * @param position
     */
    public void deleteItem(int position){
//        Log.d(TAG,"deleteItem---position=="+position+"----list.size="+list.size());
        list.remove(position);
//        Log.d(TAG,"deleteItem---position=="+position+"----after--list.size="+list.size());
        notifyItemRemoved(position);
    }

    /**
     * 添加Item
     * @param position
     * @param newsChannelBean
     */
    public void addItem(int position, NewsChannelBean newsChannelBean){
//        Log.d(TAG,"addItem---position=="+position);
        list.add(newsChannelBean);
        notifyItemInserted(position);
    }

    class NewsChannelItemHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        public NewsChannelItemHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.news_channel_tv);
        }
    }
}
