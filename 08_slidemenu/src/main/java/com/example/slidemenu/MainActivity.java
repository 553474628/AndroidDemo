package com.example.slidemenu;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lv_main;
    private ArrayList<MyBean> myBeans;

    private MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_main = (ListView) findViewById(R.id.lv_main);

        //设置适配器
        myBeans = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            myBeans.add(new MyBean("content" + i));
        }
        myAdapter = new MyAdapter();
        lv_main.setAdapter(myAdapter);
    }

    class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return myBeans.size();
        }


        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                view = View.inflate(MainActivity.this,R.layout.item_main,null);
                viewHolder = new ViewHolder();
                viewHolder.item_content = (TextView) view.findViewById(R.id.item_content);
                viewHolder.item_menu = (TextView) view.findViewById(R.id.item_menu);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            MyBean myBean = myBeans.get(i);
            viewHolder.item_content.setText(myBean.getName());

            viewHolder.item_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyBean myBean1 = myBeans.get(i);
                    Toast.makeText(MainActivity.this, myBean1.getName(), Toast.LENGTH_SHORT).show();
                }
            });

            viewHolder.item_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SlideLayout slideLayout = (SlideLayout) view.getParent();
                    slideLayout.closeMenu();
                    myBeans.remove(i);
                    notifyDataSetChanged();
                }
            });

            SlideLayout slideLayout = (SlideLayout) view;
            slideLayout.setOnStateChangeListener(new MyOnStateChangeListener());
            return view;
        }
    }

    private SlideLayout mslideLayout;

    class MyOnStateChangeListener implements SlideLayout.OnStateChangeListener{

        @Override
        public void onClose(SlideLayout slideLayout) {
            if (slideLayout == mslideLayout) {
                mslideLayout = null;
            }
        }

        @Override
        public void onDown(SlideLayout slideLayout) {
            if (mslideLayout != null && mslideLayout != slideLayout) {
                mslideLayout.closeMenu();
            }
        }

        @Override
        public void onOpen(SlideLayout slideLayout) {
            mslideLayout = slideLayout;
        }
    }

    static class ViewHolder{
        TextView item_content;
        TextView item_menu;
    }
}
