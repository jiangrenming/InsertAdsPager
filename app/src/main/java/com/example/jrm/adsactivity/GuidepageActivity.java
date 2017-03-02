package com.example.jrm.adsactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by Sun_tao on 2016/5/10.
 * 启动页
 */
public class GuidepageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidepage);
        GlobalApplication.setFristStart(true);
        Intent intent = new Intent(GuidepageActivity.this, MainActivity.class);
        startMain(intent, 5000);
    }


    public void startMain(final Intent intent, int time) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, time);
    }
}
