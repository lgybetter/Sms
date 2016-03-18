package com.lgybetter.smsproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import com.lgybetter.smsproject.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import service.LoadAddressBookData;
import service.LoadMessageData;

/**
 * Created by Administrator on 2016/2/22.
 */
public class GuideActivity extends Activity {
    Button start;

    private int [] guidePhotos = new int[]{
        R.drawable.viewpager1,R.drawable.viewpager2,R.drawable.viewpager3
    };

    private List<ImageView> guidePhotoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.guid_view);
        start = (Button)findViewById(R.id.start);
        //添加动画
//        guideViewPager.setPageTransformer(true,new DepthPageTransformer());
        ViewPager guideViewPager = (ViewPager) findViewById(R.id.viewpager);
        guideViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 2) {
                    start.setVisibility(View.VISIBLE);
                } else {
                    start.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        guideViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return guidePhotos.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(getApplicationContext());
                InputStream is = getApplicationContext().getResources().openRawResource(guidePhotos[position]);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = false;
                Bitmap btp = BitmapFactory.decodeStream(is, null, options);
                imageView.setImageBitmap(btp);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                container.addView(imageView);
                guidePhotoList.add(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(guidePhotoList.get(position));
            }
        });
        /*
        * 读取数据库，创建本地数据库
        * */
        final Intent intent = new Intent(GuideActivity.this, LoadAddressBookData.class);
        startService(intent);
        Intent intent1 = new Intent(GuideActivity.this, LoadMessageData.class);
        startService(intent1);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(GuideActivity.this, SmsMainActivity.class);
                startActivity(intent2);
                finish();
            }
        });
    }
}
