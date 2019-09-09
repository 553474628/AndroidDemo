package com.example.myviewpager;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    private MyViewPager myViewPager;
    private RadioGroup rg_main;
    private int[] ids = {R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a2,R.drawable.a3,R.drawable.a1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myViewPager = (MyViewPager) findViewById(R.id.myViewPager);
        rg_main = (RadioGroup) findViewById(R.id.rg_main);

        //添加页面
        for (int i = 0; i < ids.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(ids[i]);

            //添加到MyViewPager这个View中
            myViewPager.addView(imageView);
        }

        View testView = View.inflate(this,R.layout.test,null);
        //添加测试页面
        myViewPager.addView(testView,1);

        for (int i = 0; i < myViewPager.getChildCount(); i++) {
            RadioButton button = new RadioButton(this);
            button.setId(i);
            if (i == 0) {
                button.setChecked(true);
            }
            rg_main.addView(button);
        }

        //选中的状态变化
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                myViewPager.scrollToPager(checkedId);
            }
        });

        myViewPager.setOnPagerChangeListener(new MyViewPager.OnPagerChangeListener() {
            @Override
            public void onScrollToPager(int position) {
                rg_main.check(position);
            }
        });

    }

}
