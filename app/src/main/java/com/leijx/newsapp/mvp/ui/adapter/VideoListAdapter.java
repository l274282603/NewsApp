package com.leijx.newsapp.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.leijx.newsapp.R;
import com.leijx.newsapp.bean.VideoDataBean;

import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

;

/**
 * Created by leijx on 2017/11/14.
 */

public class VideoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<VideoDataBean> list;
    public  VideoListAdapter(Context context, List<VideoDataBean> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video_layout, parent, false);
        VideoListHolder videoListHolder = new VideoListHolder(view);
        return videoListHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VideoListHolder videoListHolder = (VideoListHolder) holder;
        VideoDataBean videoDataBean = list.get(position);
        videoListHolder.tv_play_time.setText(videoDataBean.getPtime());
        Glide.with(context).load(videoDataBean.getTopicImg())
                .asBitmap()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.color.image_place_holder)
                .centerCrop()
                .error(R.drawable.ic_load_fail)
                .into(videoListHolder.logo);
        videoListHolder.tv_from.setText(videoDataBean.getTopicName());
        videoListHolder.jzVideoPlayerStandard.setUp(videoDataBean.getMp4_url(), JZVideoPlayerStandard.SCREEN_WINDOW_LIST,
                videoDataBean.getDescription());

        Glide.with(context).load(videoDataBean.getCover())
                .asBitmap()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.color.image_place_holder)
                .centerCrop()
                .error(R.drawable.ic_load_fail)
                .into(videoListHolder.jzVideoPlayerStandard.thumbImageView);

    }

    @Override
    public int getItemCount() {
        if(list != null){
            return list.size();
        }else{
            return 0;
        }
    }


    public class VideoListHolder extends RecyclerView.ViewHolder{

        private JZVideoPlayerStandard jzVideoPlayerStandard;
        private TextView tv_from, tv_play_time;
        private ImageView logo;
        public VideoListHolder(View itemView) {
            super(itemView);
            jzVideoPlayerStandard = (JZVideoPlayerStandard ) itemView.findViewById(R.id.jZVideoPlayerStandard);
            tv_from = (TextView) itemView.findViewById(R.id.tv_from);
            tv_play_time = (TextView) itemView.findViewById(R.id.tv_play_time);
            logo = (ImageView) itemView.findViewById(R.id.logo);
        }
    }

    public void setList(List<VideoDataBean> list){

        this.list = list;
        notifyDataSetChanged();
    }
}
