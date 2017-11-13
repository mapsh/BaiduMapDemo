package com.mapsh.baidumap.demo;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * @author mapsh on 2017/11/13 10:34.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
    }
}
