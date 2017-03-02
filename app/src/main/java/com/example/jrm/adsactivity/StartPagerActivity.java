package com.example.jrm.adsactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * Created by jrm on 2016/5/10.
 * 开始页
 */
public class StartPagerActivity extends Activity {

    private ViewPager viewPager;
    private ArrayList<ImageBean> urls;
    private int TimeCache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startpage);
        initView();
        //是否第一次打开
        if (GlobalApplication.IsFristStart()) { //第一次打开应用，走这里
            //如果缓存广告数据了
            if (GlobalApplication.IsAdvertisementCache()) { //缓存了广告走这里
                initAdvertise();
                return;
            } else {
                Intent intent = new Intent(StartPagerActivity.this, MainActivity.class);
                startMain(intent, 2000);
            }
        } else { //第一次安装应用的时候走这里
            Intent intent = new Intent(StartPagerActivity.this, GuidepageActivity.class);
            startMain(intent, 2000);
        }
    }


    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        skipColock = (LinearLayout) findViewById(R.id.skipColock);
        clock = (TextView) findViewById(R.id.clock);
    }

    private AdvertisementAdatper adatper;
    private LinearLayout skipColock;
    private TextView clock;
    private Handler handler = new Handler();
    private ScheduledExecutorService scheduledExecutorService;
    private int currentItem; //当前页面
    long ShowTime;
    /**
     * 计算倒计时
     */
    class MyShareTask extends AsyncTask<Void, Void, Boolean>{
        TextView timeView;
        int limit_time = 0;
        MyShareTask(TextView timeView, int time){
            this.timeView = timeView;
            this.limit_time = time;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            while(limit_time >= 0){
                handler.post(new Runnable(){
                    @Override
                    public void run() {
                        timeView.setText( limit_time+"");
                    }
                });
                SystemClock.sleep(1000);
                limit_time--;
                if (limit_time == 0){
                    Intent intent = new Intent(StartPagerActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
            return null;
        }
    }

    /**
     * 这里当有缓存时，先走这里带有广告的页面，或者点击跳过广告也可以调转
     */
    private void initAdvertise() {
        urls = GlobalApplication.getAdvertisementJsonCache();
        TimeCache = 3;
        ShowTime = TimeCache * 1000;
        adatper = new AdvertisementAdatper(this, urls);
        viewPager.setAdapter(adatper);
        skipColock.setVisibility(View.VISIBLE);
        initTime(); //广告的数量(有可能是多个广告)
        new MyShareTask(clock,TimeCache).execute(); //倒计时的开始
        skipColock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartPagerActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void initTime() {
        long time = (long) (ShowTime / urls.size() * 1.0f);
        time = time + 300;
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        //每隔2秒钟切换一张图片
        scheduledExecutorService.scheduleWithFixedDelay(new ViewPagerTask(), time, time, TimeUnit.MILLISECONDS);
    }


    public void startMain(final Intent intent, int time) {
        startMain(intent, time, true);
    }

    public void startMain(final Intent intent, int time, final boolean flag) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, time);
    }


    //切换图片
    private class ViewPagerTask implements Runnable {
        @Override
        public void run() {
            currentItem = (currentItem + 1) % urls.size();
            handler1.obtainMessage().sendToTarget();
        }
    }


    private Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //设置当前页面
            viewPager.setCurrentItem(currentItem);
        }
    };
}
