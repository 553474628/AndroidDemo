package com.example.quickindex;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private ListView lv_main;
    private TextView tv_word;
    private IndexView iv_words;

    private Handler handler = new Handler();
    private ArrayList<Object> persons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_main = (ListView) findViewById(R.id.lv_main);
        tv_word = (TextView) findViewById(R.id.tv_word);
        iv_words = (IndexView) findViewById(R.id.iv_words);
        //设置监听字母下标索引的变化
        iv_words.setOnIndexChangeListener(new IndexView.OnIndexChangeListener() {
            @Override
            public void onIndexChange(String word) {
                //显示
                tv_word.setVisibility(View.VISIBLE);
                tv_word.setText(word);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //也是运行在主线程
                       tv_word.setVisibility(View.INVISIBLE);
                    }
                },2000);
            }
        });
    }

    public void initData() throws BadHanyuPinyinOutputFormatCombination {
        persons.add(new Person("陈彬"));
        persons.add(new Person("啊a"));
        persons.add(new Person("哎哎哎"));
        persons.add(new Person("存储"));
        persons.add(new Person("操场"));
        persons.add(new Person("订单"));
        persons.add(new Person("弟弟"));
        persons.add(new Person("嗯嗯"));
        persons.add(new Person("恩嗯"));
        persons.add(new Person("方法"));
        persons.add(new Person("仿佛"));
        persons.add(new Person("哈哈"));
        persons.add(new Person("哥哥"));
        persons.add(new Person("更改"));
        persons.add(new Person("一"));
        persons.add(new Person("解决"));
        persons.add(new Person("看看"));
        persons.add(new Person("凉了"));
        persons.add(new Person("密码"));
        persons.add(new Person("那你"));
        persons.add(new Person("哦哦"));
        persons.add(new Person("泡泡"));
        persons.add(new Person("请求"));

        //排序
        

    }
}
