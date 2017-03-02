package com.example.jrm.adsactivity;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sun_tao on 2016/5/10.
 */
public class GlobalApplication extends Application {

    /**
     * 应用实例
     */
    public static GlobalApplication mapplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mapplication = this;
    }


    /**
     * 是否第一次启动
     */
    public static boolean IsFristStart() {
        return (boolean) SPUtil.get(mapplication, AppConfig.KEY_FRITST_START, false);
    }


    public static void setFristStart(boolean frist) {
        SPUtil.putAndApply(mapplication, AppConfig.KEY_FRITST_START, frist);
    }


    /**
     * 广告是否缓存
     *
     * @return
     */
    public static boolean IsAdvertisementCache() {
        return (boolean) SPUtil.get(mapplication, AppConfig.IsAdvertisementCache, false);
    }

    public static void setAdvertisementCache(boolean frist) {
        SPUtil.putAndApply(mapplication, AppConfig.IsAdvertisementCache, frist);
    }


    /**
     * 广告json 缓存
     * @param frist
     */
    public static void setAdvertisementJsonCache(ArrayList<ImageBean> frist) {
        Gson gson = new Gson();
        String json = gson.toJson(frist);
        SPUtil.putAndApply(mapplication, AppConfig.IsAdvertisementJsonCache, json);
    }

    /**
     * 广告json 获取缓存
     */
    public static ArrayList<ImageBean> getAdvertisementJsonCache() {
        Gson gson = new Gson();
        String json1 = (String) SPUtil.get(mapplication, AppConfig.IsAdvertisementJsonCache, "");
        ArrayList<ImageBean> advert = gson.fromJson(json1, new TypeToken<List<ImageBean>>() {
        }.getType());
        return advert;
    }


    /**
     * 获取广告 缓存
     *
     * @param frist
     */
    public static void setAdvertisementTimeCache(String frist) {
        SPUtil.putAndApply(mapplication, AppConfig.IsAdvertisementJsonCacheTime, frist);
    }

    /**
     * 广告时间 获取缓存
     */
    public static String getAdvertisementTimeCache() {
        String json1 = (String) SPUtil.get(mapplication, AppConfig.IsAdvertisementJsonCacheTime, "0");
        return json1;
    }

}
