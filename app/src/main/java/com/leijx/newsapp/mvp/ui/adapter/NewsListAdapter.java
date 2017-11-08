package com.leijx.newsapp.mvp.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.leijx.newsapp.App;
import com.leijx.newsapp.R;
import com.leijx.newsapp.bean.Newsbean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leijx on 2017/11/2.
 */

public class NewsListAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private List<Newsbean> listnews;
    private Bitmap bitmap;
    //    private MyViewHholder myViewHholder;
    private OnItemsOnClicklistener onItemsOnClicklistener;
    private boolean isloadingmore = false;

    private static final int Item_LoadingMoreView = 0; //加载更多布局
    private static final int Item_NewsView = 1;        //新闻布局
    private static final int Item_SliderView = 2;  //轮播图片布局
//    private static final int Item_ImageNewsView = 2;

    private int count = 0;
    private int lastposition = -1;


    private static  final String TAG ="NewsAdapter_leijx";
    public NewsListAdapter(Context context){
        this.context = context;
    }
    public void setlist(List<Newsbean> list){
        listnews=list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG,"viewType==="+viewType);
        if (viewType == Item_LoadingMoreView){
            View view = LayoutInflater.from(context).inflate(R.layout.item_loadingmore_layout,parent,false);
            MyLoadingViewHolder myLoadingViewHolder = new MyLoadingViewHolder(view);
            return myLoadingViewHolder;

        }else if (viewType == Item_NewsView){
            View view = LayoutInflater.from(context).inflate(R.layout.item_news_layout,parent,false);
            final MyViewHolder myViewHholder = new MyViewHolder(view);
            myViewHholder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemsOnClicklistener.onItemOnClick(v,myViewHholder.getLayoutPosition());
                }
            });
            return myViewHholder;
        }
        else if(viewType == Item_SliderView){
            View view = LayoutInflater.from(context).inflate(R.layout.item_sliderview_layout, parent, false);
            MySliderViewHolder mySliderViewHolder =new MySliderViewHolder(view);
            return mySliderViewHolder;
        }
//        else if (viewType == Item_ImageNewsView){
//            View view =LayoutInflater.from(context).inflate(R.layout.item_imagenews_layout,parent, false);
//            final MyImageViewHolder myImageViewHolder =new MyImageViewHolder(view);
//            myImageViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onItemsOnClicklistener.onItemOnClick(v,myImageViewHolder.getLayoutPosition());
//                }
//            });
//            return myImageViewHolder;
//        }
        return null;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        myViewHholder = holder;
        if(holder instanceof MyViewHolder){
            final MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.title.setText(listnews.get(position).getTitle());
            myViewHolder.summary.setText(listnews.get(position).getDigest());
            myViewHolder.time.setText(listnews.get(position).getPtime());
            //根据对应url，加载图片
            Glide.with(App.getsAppContext()).load(listnews.get(position).getImgsrc())
                    .asBitmap()
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.color.image_place_holder)
                    .error(R.drawable.ic_load_fail)
                    .into(myViewHolder.imageView);

        }
        else if(holder instanceof MySliderViewHolder){
            initSliderLayout((MySliderViewHolder) holder);
        }

//        else if(holder instanceof MyImageViewHolder){
//            final MyImageViewHolder myImageViewHolder = (MyImageViewHolder) holder;
//            myImageViewHolder.title.setText("图集："+listnews.get(position).getAds().get(0).getTitle());
//            myImageViewHolder.time.setText(listnews.get(position).getPtime());
//            int imagecount = listnews.get(position).getAds().size();
//            if(imagecount >=3){
//                myImageViewHolder.leftimg.setVisibility(View.VISIBLE);
//                myImageViewHolder.mideimg.setVisibility(View.VISIBLE);
//                myImageViewHolder.rightimg.setVisibility(View.VISIBLE);
//            }else if (imagecount >=2){
//                myImageViewHolder.leftimg.setVisibility(View.VISIBLE);
//                myImageViewHolder.mideimg.setVisibility(View.VISIBLE);
//                myImageViewHolder.rightimg.setVisibility(View.GONE);
//            }else{
//                myImageViewHolder.leftimg.setVisibility(View.VISIBLE);
//                myImageViewHolder.mideimg.setVisibility(View.GONE);
//                myImageViewHolder.rightimg.setVisibility(View.GONE);
//            }
//            count = 0;
//            for (int i=0; i<listnews.get(position).getAds().size();i++){
//                if(i>=3){
//                    break;
//                }
//                final int j = i;
//                RxJavaUtil.getimage(context, listnews.get(position).getAds().get(j).getImgsrc(), new RxJavaUtil.Callback_getImage() {
//                    @Override
//                    public void sucess(Drawable bitmap) {
////                        count++;
//                        if(j == 0){
//                            myImageViewHolder.leftimg.setBackgroundDrawable(bitmap);
//                        }else if(j == 1){
//                            myImageViewHolder.mideimg.setBackgroundDrawable(bitmap);
//                        }else if(j == 2){
//                            myImageViewHolder.rightimg.setBackgroundDrawable(bitmap);
//                        }
//                    }
//
//                    @Override
//                    public void sucess(String src, Drawable bitmap) {
//                        Log.d(TAG,"j==="+j);
//                        Log.d(TAG,"SRC==="+src);
//
//                    }
//
//                    @Override
//                    public void faile() {
//                        count++;
//
//                    }
//                });
//            }
//
//        }
//        setItemAnim(position, holder);
    }

    /**
     * 设置加载Item时的动画
     * @param position
     * @param holder
     */
    private void setItemAnim(int position, ViewHolder holder) {
        if(position>lastposition){
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.item_anim);
            holder.itemView.startAnimation(animation);
            lastposition = position;
        }
    }

    private void initSliderLayout( MySliderViewHolder mySliderViewHolder){

        Log.d(TAG,"initSliderLayout");
        List<Newsbean.adsBean> adsBeanList =new ArrayList<Newsbean.adsBean>();
        for(int i =0; i<listnews.size(); i++){
            if(listnews.get(i).getAds()!=null && listnews.get(i).getAds().size()!=0){
                adsBeanList = listnews.get(i).getAds();
            }
        }
        for (int j = 0;j<adsBeanList.size();j++){
            TextSliderView textSliderView =new TextSliderView(context);
            textSliderView.description(adsBeanList.get(j).getTitle());  //设置标题
            textSliderView.image(adsBeanList.get(j).getImgsrc());    //设置图片地址
            textSliderView.setScaleType(BaseSliderView.ScaleType.CenterCrop); //设置图片缩放效果
            mySliderViewHolder.sliderLayout.addSlider(textSliderView);
        }
        mySliderViewHolder.sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);  //设置指示器位置
//        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion); //设置图片的切换效果
        mySliderViewHolder.sliderLayout.setCustomAnimation(new DescriptionAnimation()); //添加textView动画特效
        mySliderViewHolder.sliderLayout.setDuration(3000);



    }
    @Override
    public int getItemCount() {
        if(listnews==null){
            return 0;
        }else{
            if(isloadingmore){   //如果是需要显示加载更多布局，那么Item个数需要在当前获取来的新闻总数量上加1
                return listnews.size()+1;
            }else{
                return listnews.size();
            }

        }
    }

    /**
     * 根据position 返回不同的布局类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
//        Log.d(TAG,"getItemViewType---position="+position);
//        Log.d(TAG,"getItemViewType---isloadingmore="+isloadingmore);
//        Log.d(TAG,"getItemViewType---getItemCount="+getItemCount());
        List<Newsbean.adsBean> adsBeanList =new ArrayList<Newsbean.adsBean>();  //用来存储图片新闻对象
        for(int i =0; i<listnews.size(); i++){
            if(listnews.get(i).getAds()!=null && listnews.get(i).getAds().size()!=0){
                adsBeanList = listnews.get(i).getAds();
            }
        }
        if(position==0 && adsBeanList.size()!=0){  //对于图片新闻，通过轮播的一个布局来实现显示
            return Item_SliderView;
        }else if(isloadingmore && ( position == getItemCount()-1)){  //如果是当前获取来新闻的最后一个，则显示加载更多 布局
            return Item_LoadingMoreView;
        }
//        else if(listnews.get(position).getAds()!=null && listnews.get(position).getAds().size()!=0){
//            return Item_ImageNewsView;
//        }
        else{                                      //否则显示正常新闻布局
            return Item_NewsView;
        }
    }

    public boolean isIsloadingmore(){
        return isloadingmore;
    }

    /**
     * 显示正在加载更多
     */
    public void showloadingmore(){
        isloadingmore = true;
        notifyDataSetChanged();
    }

    /**
     * 隐藏加载更多
     */
    public void hideloadingmore(){
        isloadingmore = false;
        notifyDataSetChanged();
    }

    public void setOnItemsOnClicklistener(OnItemsOnClicklistener onItemsOnClicklistener){
        this.onItemsOnClicklistener = onItemsOnClicklistener;
    }
    public interface OnItemsOnClicklistener{
        public void onItemOnClick(View v, int position);
    }
}


class MyViewHolder extends RecyclerView.ViewHolder{

    public TextView title;
    public ImageView imageView;
    public TextView summary;
    public TextView time;
    public MyViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.newstitle);
        imageView = (ImageView) itemView.findViewById(R.id.image);
        summary = (TextView) itemView.findViewById(R.id.summary);
        time = (TextView) itemView.findViewById(R.id.time);
    }
}

class MyLoadingViewHolder extends ViewHolder{

    public MyLoadingViewHolder(View itemView) {
        super(itemView);
    }
}

class MySliderViewHolder extends ViewHolder{

    public SliderLayout sliderLayout;
    public MySliderViewHolder(View itemView) {
        super(itemView);
        sliderLayout = (SliderLayout) itemView.findViewById(R.id.sliderLayout);
    }
}

class MyImageViewHolder extends ViewHolder{
    public TextView title;
    public ImageView leftimg;
    public ImageView mideimg;
    public ImageView rightimg;
    public TextView time;

    public MyImageViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.imagenewstitle);
        leftimg = (ImageView) itemView.findViewById(R.id.left_img);
        mideimg = (ImageView) itemView.findViewById(R.id.mide_img);
        rightimg = (ImageView) itemView.findViewById(R.id.right_img);
        time = (TextView) itemView.findViewById(R.id.time);
    }
}
