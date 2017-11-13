package com.mapsh.module.map

import android.support.annotation.FloatRange
import android.support.annotation.IntRange
import com.baidu.mapapi.map.BaiduMap
import com.baidu.mapapi.map.MapStatus
import com.baidu.mapapi.map.MapStatusUpdateFactory
import com.baidu.mapapi.map.MyLocationData
import com.baidu.mapapi.model.LatLng


/**
 * @author mapsh on 2017/11/8 08:53.
 */

object BDMapUtils {

    val ZOOM_5M = 21f
    val ZOOM_10M = 20f
    val ZOOM_20M = 19f
    val ZOOM_50M = 18f
    val ZOOM_100M = 17f
    val ZOOM_200M = 16f
    val ZOOM_500M = 15f
    val ZOOM_1KM = 13f
    val ZOOM_2KM = 12f
    val ZOOM_5KM = 11f
    val ZOOM_10KM = 10f
    val ZOOM_20KM = 9f
    val ZOOM_25KM = 8f
    val ZOOM_50KM = 7f
    val ZOOM_100KM = 6f
    val ZOOM_200KM = 5f
    val ZOOM_500KM = 4f
    val ZOOM_1000KM = 3f

    //百度地图缩放大级别,单位米
    private val zoom = arrayListOf(
            5,
            10,
            20,
            50,
            100,
            200,
            500,
            1000,
            2000,
            5000,
            10 * 1000,
            20 * 1000,
            25 * 1000,
            50 * 1000,
            100 * 1000,
            200 * 1000,
            500 * 1000,
            1000 * 1000)


    //显示定位点
    fun showLatLng(latLng: LatLng, map: BaiduMap) {
        val locData = MyLocationData.Builder()
                .accuracy(300f)
                .latitude(latLng.latitude)
                .longitude(latLng.longitude)
                .build()
        map.isMyLocationEnabled = true
        map.setMyLocationData(locData)
    }

    //移动到定位点
    fun moveToLatLng(latLng: LatLng?, map: BaiduMap) {
        val builder = MapStatus.Builder()
        builder.target(latLng)
        map.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))
    }

    //移动到定位点
    fun moveToLatLngAndZoom(latLng: LatLng?, map: BaiduMap, @FloatRange(from = 3.0, to = 21.0) zoom: Float) {
        if (latLng == null) return
        var z = zoom
        if (z < 3 || z > 21) {
            z = 16f
        }

        val builder = MapStatus.Builder()
        builder.target(latLng).zoom(z)//标尺200米
        map.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))
    }


    /** *根据距离判断地图级别
     */
    fun zoomLevel(distance: Int, map: BaiduMap) {
        for (i in zoom.indices) {
            val zoomNow = zoom[i]
            if (zoomNow - distance * 1000 > 0) {
                var level = 21 - i + 10
                //设置地图显示级别为计算所得level
                map.setMapStatus(MapStatusUpdateFactory.newMapStatus(MapStatus.Builder().zoom(level.toFloat()).build()))
                break
            }
        }
    }

    /**
     * 计算中心点经纬度，将其设为启动时地图中心
     */
    fun setCenter(latLngList: List<LatLng>, map: BaiduMap) {
        if (latLngList.size > 1) {
            val maxLatitude = latLngList.maxBy { it.latitude }!!.latitude
            val minLatitude = latLngList.minBy { it.latitude }!!.latitude
            val maxLongitude = latLngList.maxBy { it.longitude }!!.longitude
            val minLongitude = latLngList.minBy { it.longitude }!!.longitude

            val center = LatLng((maxLatitude + minLatitude) / 2, (maxLongitude + minLongitude) / 2)
            val status = MapStatusUpdateFactory.newLatLng(center)
            map.animateMapStatus(status, 500)
        } else if (latLngList.size == 1) {
            val status = MapStatusUpdateFactory.newLatLng(latLngList[0])
            map.animateMapStatus(status, 500)
        }
    }

}
