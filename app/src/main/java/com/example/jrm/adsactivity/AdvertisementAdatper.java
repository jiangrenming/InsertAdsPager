package com.example.jrm.adsactivity;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * creator :  tlp
 * time    :  2016/5/4.
 * content :
 */
public class AdvertisementAdatper extends PagerAdapter {

    private Context mcontext;

    private List<ImageBean> murls;

    public AdvertisementAdatper(Context context, List<ImageBean> urls) {
        mcontext = context;
        murls = urls;

    }


    @Override
    public int getCount() {
        if (murls == null) return 0;
        return murls.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LinearLayout mitemView = (LinearLayout) LayoutInflater.from(mcontext).inflate(R.layout.item_advertisement, null);
        ImageView imageView = (ImageView) mitemView.findViewById(R.id.image);
        Glide.with(mcontext).load(murls.get(position).getImg_thumb()).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
        container.addView(mitemView);
        return mitemView;
    }
}
