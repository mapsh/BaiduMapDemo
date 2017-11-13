package com.mapsh.baidumap.demo

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.baidu.mapapi.map.MyLocationData
import com.baidu.mapapi.model.LatLng
import com.mapsh.module.map.BDMapUtils
import com.mapsh.module.map.LocationLiveData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        LocationLiveData.get(this).observe(this, Observer { bdLocation ->
            if (bdLocation == null) {
                return@Observer
            }
            val locData = MyLocationData.Builder()
                    .accuracy(50f)
                    .latitude(bdLocation.latitude)
                    .longitude(bdLocation.longitude)
                    .build()

            mapView.map.apply {
                isMyLocationEnabled = true
                setMyLocationData(locData)
                BDMapUtils.moveToLatLngAndZoom(LatLng(bdLocation.latitude, bdLocation.longitude), this,BDMapUtils.ZOOM_50M)
            }
        })

    }
}
