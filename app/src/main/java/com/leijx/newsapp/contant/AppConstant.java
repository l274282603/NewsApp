package com.leijx.newsapp.contant;

/**
 * Created by leijx on 2017/10/31.
 */

public class AppConstant {
    public static final String HOME_CURRENT_TAB_POSITION="HOME_CURRENT_TAB_POSITION";
    public static final String DB_NAME = "NewsChannelTable";
    public static final String INIT_DB = "init_db";


    public static final String NEWCHANNEL_ID="newchannel_id";
    public static final String NEWCHANNEL_TYPE="newchannel_type";

    public static final String VideoTYPEID = "VideoTYPEID";

    public static final String BASEURL_News="http://c.m.163.com/";
    public static final String BASEURL_Photo="http://gank.io/api/";

    // 头条TYPE
    public static final String HEADLINE_TYPE = "headline";
    // 房产TYPE
    public static final String HOUSE_TYPE = "house";
    // 其他TYPE
    public static final String OTHER_TYPE = "list";

    // 头条id
    public static final String HEADLINE_ID = "T1348647909107";
    // 房产id
    public static final String HOUSE_ID = "5YyX5Lqs";

    /**
     * 新闻id获取类型
     *
     * @param id 新闻id
     * @return 新闻类型
     */
    public static String getType(String id) {
        switch (id) {
            case HEADLINE_ID:
                return HEADLINE_TYPE;
            case HOUSE_ID:
                return HOUSE_TYPE;
            default:
                break;
        }
        return OTHER_TYPE;
    }


    /**新闻加载类型**/
    public static final int REFERSHNEWS_SUCESS = 1;  //刷新列表成功
    public static final int REFERSHNEWS_FAIL = 0;  //刷新列表失败
    public static final int LOADMORE_SUCESS = 2;        //加载更多成功
    public static final int LOADMORE_FAIL = 3;        //加载更多失败

    /**photo加载类型**/
    public static final int REFERSHPHOTO_SUCESS = 1;  //刷新列表成功
    public static final int REFERSHPHOTO_FAIL = 0;  //刷新列表失败
    public static final int LOADMOREPHOTO_SUCESS = 2;        //加载更多成功
    public static final int LOADMOREPHOTO_FAIL = 3;        //加载更多失败

    /**Video加载类型**/
    public static final int REFERSHVIDEO_SUCESS = 1;  //刷新列表成功
    public static final int REFERSHVIDEO_FAIL = 0;  //刷新列表失败
    public static final int LOADMOREVIDEO_SUCESS = 2;        //加载更多成功
    public static final int LOADMOREVIDEO_FAIL = 3;        //加载更多失败


    public static final int News = 1;
    public static final int Photo = 2;
    public static final int Video = 3;

    public static String getBaseUrl(int type){
        switch (type){
            case News:
                return BASEURL_News;
            case Photo:
                return BASEURL_Photo;
            case Video:
                return BASEURL_News;
            default:
                break;
        }
        return BASEURL_News;
    }

    public static final String PHOTOSRC = "PhotoSrc";

}
