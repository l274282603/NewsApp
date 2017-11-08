package com.leijx.newsapp.greendao;

import com.leijx.newsapp.App;
import com.leijx.newsapp.R;
import com.leijx.newsapp.bean.NewsChannelBean;
import com.leijx.newsapp.contant.AppConstant;
import com.leijx.newsapp.util.SharePrefHelper;

import org.greenrobot.greendao.query.Query;

import java.util.List;

/**
 * GreenDao工具类
 * Created by leijx on 2017/11/1.
 */

public class GreendaoDBManager {

    public static void initDB(){
        //第一次进应用，初始化数据库中的数据
        if(!SharePrefHelper.getInstance().getboolean(AppConstant.INIT_DB)){
            NewsChannelBeanDao newsChannelBeanDao = App.getNewsChannelBeanDao();
            String[] namearray = App.getsAppContext().getResources().getStringArray(R.array.news_channel_name);
            String[] idarray = App.getsAppContext().getResources().getStringArray(R.array.news_channel_id);
            for(int i=0; i<namearray.length; i++){
                NewsChannelBean newsChannelBean = new NewsChannelBean(namearray[i],idarray[i]
                        ,AppConstant.getType(idarray[i]), i, i <= 5, i == 0);
                newsChannelBeanDao.insert(newsChannelBean);  //插入一条数据
            }
            SharePrefHelper.getInstance().putboolean(AppConstant.INIT_DB, true);
        }

    }

    /**
     * 获取我的 新闻频道列表
     * @return
     */
    public static List<NewsChannelBean> getNewsChannel_My(){
        Query<NewsChannelBean> newsChannelBeanQuery = App.getNewsChannelBeanDao().queryBuilder()
                .where(NewsChannelBeanDao.Properties.NewChannelSelect.eq(true))
                .orderAsc(NewsChannelBeanDao.Properties.NewsChannelIndex)
                .build();
        return newsChannelBeanQuery.list();
    }

    /**
     * 获取更多 新闻频道列表
     * @return
     */
    public static List<NewsChannelBean> getNewsChannel_More(){
        Query<NewsChannelBean> newsChannelBeanQuery = App.getNewsChannelBeanDao().queryBuilder()
                .where(NewsChannelBeanDao.Properties.NewChannelSelect.eq(false))
                .orderAsc(NewsChannelBeanDao.Properties.NewsChannelIndex)
                .build();
        return newsChannelBeanQuery.list();
    }

    /**
     * 获取NewsChannelIndex在 fromindex到toindex之间的新闻频道列表
     * @param fromindex
     * @param toindex
     * @return
     */
    public static List<NewsChannelBean> getNewsChannel_fromto(int fromindex, int toindex){
        Query<NewsChannelBean> newsChannelBeanQuery = App.getNewsChannelBeanDao().queryBuilder()
                .where(NewsChannelBeanDao.Properties.NewsChannelIndex.between(fromindex,toindex))
                .orderAsc(NewsChannelBeanDao.Properties.NewsChannelIndex)
                .build();
        return newsChannelBeanQuery.list();
    }

    /**
     * 获取NewsChannelIndex 比index大的所有新闻频道列表
     * @param index
     * @return
     */
    public static List<NewsChannelBean> getNewsChannel_IndexGT(int index){
        Query<NewsChannelBean> newsChannelBeanQuery = App.getNewsChannelBeanDao().queryBuilder()
                .where(NewsChannelBeanDao.Properties.NewsChannelIndex.gt(index))
                .orderAsc(NewsChannelBeanDao.Properties.NewsChannelIndex)
                .build();
        return newsChannelBeanQuery.list();
    }

    /**
     *
     * @param channelIndex
     * @return
     */
    public static List<NewsChannelBean> loadNewsChannelsIndexLtAndIsUnselect(int channelIndex) {
        Query<NewsChannelBean> newsChannelTableQuery = App.getNewsChannelBeanDao().queryBuilder()
                .where(NewsChannelBeanDao.Properties.NewsChannelIndex.lt(channelIndex),
                        NewsChannelBeanDao.Properties.NewChannelSelect.eq(false)).build();
        return newsChannelTableQuery.list();
    }

    /**
     * 获取全部新闻频道个数
     * @return
     */
    public static int getAllsize(){
        return App.getNewsChannelBeanDao().loadAll().size();
    }

    /**
     * 获取我的新闻频道 个数
     * @return
     */
    public static int getNewschannelSelectSize(){
        return (int) App.getNewsChannelBeanDao().queryBuilder()
                .where(NewsChannelBeanDao.Properties.NewChannelSelect.eq(true))
                .buildCount()
                .count();
    }

    /**
     * 更新频道信息
     * @param newsChannelBean
     */
    public static void updateNewsChannel(NewsChannelBean newsChannelBean){
        App.getNewsChannelBeanDao().update(newsChannelBean);
    }


}
