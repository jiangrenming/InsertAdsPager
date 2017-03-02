package com.example.jrm.adsactivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<ImageBean> imageCache = new ArrayList<>();
    /**
     * 模拟网络更新时间
     */
    private int HttpImageTime = 20160704;
    /**
     * 模拟广告的数量
     */
    private int HttpImageCount = 2;

    private int currentTarget = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        loadImageCache(HttpImageTime);
    }

    private void loadImageCache(int time) {
        /**
         * 1，第一次必然需要缓存。
         * 2.第二次的时候就需要对比后端更新的时间.
         *
         */
        int cacheTime = Integer.parseInt(GlobalApplication.getAdvertisementTimeCache());
        if (time > cacheTime) {
            //如果新的时间更新时间大于缓存，开始更新
            for (ImageBean bean : imageCache) {
                new ShareTask(MainActivity.this).execute(bean.getImg_thumb());
            }

        }
    }


    private void initData() {
        //不好模拟环境 就自己造数据吧
        ImageBean image1 = new ImageBean();
        image1.setImg_thumb("http://img.blog.csdn.net/20160408225549705");
        ImageBean image2 = new ImageBean();
        image2.setImg_thumb("http://img.blog.csdn.net/20160409010141902");
        imageCache.add(image1);
        imageCache.add(image2);
    }

    //  广告缓存
    class ShareTask extends AsyncTask<String, Void, File> {
        private final Context context;

        public ShareTask(MainActivity context) {
            this.context = context;
        }

        @Override
        protected File doInBackground(String... params) {
            String url = params[0]; // should be easy to extend to share multiple images at once
            try {
                //  这个作用 就是下载网络原图大小的尺寸。
                return Glide.with(context).load(url).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get(); // needs to be called on background thread;
            } catch (Exception ex) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(File result) {
            currentTarget++;
            //这样判断方式没有缓存完毕。
            if (currentTarget == HttpImageCount) {
                GlobalApplication.setAdvertisementJsonCache(imageCache);
                GlobalApplication.setAdvertisementTimeCache(HttpImageTime + "");
                GlobalApplication.setAdvertisementCache(true);
            }
        }
    }
}
