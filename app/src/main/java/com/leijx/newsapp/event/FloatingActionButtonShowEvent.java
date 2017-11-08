package com.leijx.newsapp.event;

/**
 * Created by leijx on 2017/11/6.
 */

public class FloatingActionButtonShowEvent {
    private boolean isvisiable;
    public boolean getIsvisiable(){
        return isvisiable;
    }

    public FloatingActionButtonShowEvent(boolean isvisiable){
        this.isvisiable = isvisiable;
    }
}
