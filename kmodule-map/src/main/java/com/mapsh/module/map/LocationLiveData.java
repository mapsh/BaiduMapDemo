package com.mapsh.module.map;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * @author mapsh on 2017/10/31 10:06.
 */

public class LocationLiveData extends LiveData<BDLocation> {

    private volatile static LocationLiveData sInstance;

    private LocationClient mLocClient;

    private LocationLiveData(Context context) {

        mLocClient = new LocationClient(context.getApplicationContext());
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        option.setScanSpan(3000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setIsNeedLocationDescribe(true);//可选，设置是否需要地址描述
        option.setNeedDeviceDirect(false);//可选，设置是否需要设备方向结果
        option.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setIsNeedAltitude(false);//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        mLocClient.setLocOption(option);  //设置定位参数
    }

    public static LocationLiveData get(Context context) {
        if (sInstance == null) {
            synchronized (LocationLiveData.class) {
                if (sInstance == null) {
                    sInstance = new LocationLiveData(context);
                }
            }
        }
        return sInstance;
    }


    @Override
    protected void onActive() {
        mLocClient.registerLocationListener(mListener);
        mLocClient.start();
    }

    @Override
    protected void onInactive() {
        mLocClient.unRegisterLocationListener(mListener);
        mLocClient.stop();
    }

    private BDLocationListenerImpl mListener = new BDLocationListenerImpl();

    class BDLocationListenerImpl extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //call here only once
            if (bdLocation != null) {
                Log.e("TAG", bdLocation.getAddrStr());
            }
            setValue(bdLocation);
        }
    }
}