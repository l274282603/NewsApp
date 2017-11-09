package com.leijx.newsapp.mvp.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.leijx.newsapp.R;
import com.leijx.newsapp.bean.PhotoDataBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by leijx on 2017/11/7.
 */

public class PhotoListAdapter extends RecyclerView.Adapter<MyphotoViewHolder> {

    private Context context;
    private List<PhotoDataBean.PhotoImageBean> list;
    private static final String TAG = "PhotoListAdapter";
    private Map<Integer, Integer> heightMap = new HashMap<>();
    private int imagewidth;
    private OnItemClickListener onItemClickListener;
    public PhotoListAdapter(Context context, List<PhotoDataBean.PhotoImageBean> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public MyphotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG,"MyphotoViewHolder");
        View view = LayoutInflater.from(context).inflate(R.layout.item_photo_layout, parent, false);
        final MyphotoViewHolder myphotoViewHolder = new MyphotoViewHolder(view);
        myphotoViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v,list.get(myphotoViewHolder.getLayoutPosition()));
            }
        });
        return myphotoViewHolder;
    }

    @Override
    public void onBindViewHolder(MyphotoViewHolder holder, int position) {
        String url = list.get(position).getUrl();
//        ViewGroup.LayoutParams params = holder.imageView.getLayoutParams();
//        imagewidth = params.width;
//        imagewidth = holder.imageView.getWidth();
        if(!heightMap.containsKey(position)){
            int height = (int) (200 + Math.random() * 200);
            Log.d(TAG,"containsKey---position="+position+"height=="+height);
            heightMap.put(position, height);
//            loadImageFirst(holder.imageView, position);
        }
        ViewGroup.LayoutParams layoutParams = holder.imageView.getLayoutParams();
        layoutParams.height = heightMap.get(position);
        holder.imageView.setLayoutParams(layoutParams);
        Log.d(TAG,"onBindViewHolder-position = "+position+"--ID="+list.get(position).get_id()+"--height="+layoutParams.height);

        Glide.with(context)
                .load(url)
                .asBitmap()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.color.image_place_holder)
                .centerCrop()
                .error(R.drawable.ic_load_fail)
                .into(holder.imageView);

    }

    private void loadImageFirst(ImageView view, final int position){
        ViewTarget<ImageView, Bitmap> viewBitmapViewTarget = new ViewTarget<ImageView, Bitmap>(view) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                imagewidth = view.getWidth();
                float scalvalue = resource.getHeight()/(resource.getWidth()*1.0f);
                int imageheight = (int) scalvalue * imagewidth;
                Log.d(TAG,"onResourceReady--scalvalue="+scalvalue+"--imageheight ="+imageheight+"---imagewidth="+imagewidth);
                heightMap.put(position, imageheight);

                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.width = imagewidth;
                layoutParams.height = imageheight;
                view.setLayoutParams(layoutParams);
                view.setImageBitmap(resource);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                Log.d(TAG,"onLoadFailed");
                super.onLoadFailed(e, errorDrawable);
                imagewidth = view.getWidth();
                int imageheight = imagewidth;
                heightMap.put(position, imageheight);

                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.width = imagewidth;
                layoutParams.height = imageheight;
                view.setLayoutParams(layoutParams);
                view.setBackgroundResource(R.drawable.ic_load_fail);
            }
        };

        Glide.with(context)
                .load(list.get(position).getUrl())
                .asBitmap()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.color.image_place_holder)
//                .override(300,(int) (200 + Math.random() * 200))
//                .error(R.drawable.ic_load_fail)
                .into(viewBitmapViewTarget);

    }

    @Override
    public int getItemCount() {
//        Log.d("PhotoListAdapter_leijx","getItemCount");
        if(list!=null){
            return list.size();
        }else{
            return 0;
        }

    }

    public void setList(List<PhotoDataBean.PhotoImageBean> list){
//        Log.d("PhotoListAdapter_leijx","setList");
        this.list = list;
        heightMap.clear();
        notifyDataSetChanged();
    }


    public interface OnItemClickListener{
        void onItemClick(View v, PhotoDataBean.PhotoImageBean photoImageBean);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

}


class MyphotoViewHolder extends RecyclerView.ViewHolder{

     ImageView imageView;
    public MyphotoViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.image);
    }
}
