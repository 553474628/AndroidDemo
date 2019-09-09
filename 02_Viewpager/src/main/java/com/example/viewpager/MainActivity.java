package com.example.viewpager;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private ViewPager viewPager;
    private TextView tv_title;
    private LinearLayout l1_point_group;

    private ArrayList<ImageView> imageViews;
    private int pre = 0;

    private boolean isDragging = true;

    //图片资源id
    private final int[] imageIds = {
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d
    };

    private final String[] imageDescriptions = {
            "我爱你中国",
            "亲爱的母亲",
            "我为你流泪",
            "也为你祝福"
    };

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int item = viewPager.getCurrentItem() + 1;
            viewPager.setCurrentItem(item);

            //4秒钟发一次消息
            handler.sendEmptyMessageDelayed(0,4000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewpager);
        tv_title = findViewById(R.id.tv_title);
        l1_point_group = (LinearLayout) findViewById(R.id.l1_point_group);

        imageViews = new ArrayList<>();
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);

            imageViews.add(imageView);

            //添加点
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.point_select);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(8,8);
            if (i == 0) {
                point.setEnabled(true);
            } else {
                point.setEnabled(false);
                params.leftMargin = 8;
            }

            point.setLayoutParams(params);
            l1_point_group.addView(point);
        }

        viewPager.setAdapter(new MyPagerAdapter());
        int item = Integer.MAX_VALUE/2 - Integer.MAX_VALUE/2%imageViews.size();
        viewPager.setCurrentItem(item);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                int realPosition = i % imageViews.size();

                tv_title.setText(imageDescriptions[realPosition]);
                l1_point_group.getChildAt(pre).setEnabled(false);
                l1_point_group.getChildAt(realPosition).setEnabled(true);

                pre = realPosition;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    isDragging = true;
                } else if (state == ViewPager.SCROLL_STATE_SETTLING) {

                } else if (state == ViewPager.SCROLL_STATE_IDLE) {
                    isDragging = false;
                    handler.removeCallbacksAndMessages(null);
                    handler.sendEmptyMessageDelayed(0,4000);
                }
            }
        });

        handler.sendEmptyMessageDelayed(0,3000);
    }


    private class MyPagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
//            return imageViews.size();
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @SuppressLint("ClickableViewAccessibility")
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            int realPosition = position % imageViews.size();

            final ImageView imageView = imageViews.get(realPosition);
            container.addView(imageView);
            Log.e(TAG, "instantiateItem: " + imageView);

            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            handler.removeCallbacksAndMessages(null);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            break;
                        case MotionEvent.ACTION_UP:
                            handler.removeCallbacksAndMessages(null);
                            handler.sendEmptyMessageDelayed(0,4000);
                            break;
                    }
                    return false;
                }
            });

            imageView.setTag(position);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (int) view.getTag()%imageViews.size();
                    String text = imageDescriptions[position];
                    Toast.makeText(MainActivity.this,"text" + text,Toast.LENGTH_LONG).show();
                }
            });

            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
