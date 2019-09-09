package com.example.popupwindow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_input;
    private ImageView iv_down;

    private PopupWindow popupWindow;
    private ListView listView;

    private ArrayList<String> message;
    private MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_input = (EditText) findViewById(R.id.et_input);
        iv_down = (ImageView) findViewById(R.id.iv_down);

        iv_down.setOnClickListener(this);

        message = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            message.add(i + "--aaaaa---" + i);
        }
        listView = new ListView(this);
        myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //得到数据
                String msg = message.get(i);
                et_input.setText(msg);
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            }
        });

    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return message.size();
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
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                view = View.inflate(MainActivity.this,R.layout.item_main,null);
                viewHolder = new ViewHolder();
                viewHolder.tv_msg = (TextView) view.findViewById(R.id.tv_msg);
                viewHolder.iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            //根据位置得到数据
            final String msg =  message.get(i);
            viewHolder.tv_msg.setText(msg);

            //设置删除
            viewHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //从集合中删除
                    message.remove(msg);
                    //刷新ui，适配器刷新
                    myAdapter.notifyDataSetChanged();
                }
            });
            return view;
        }
    }

    static class ViewHolder {
        TextView tv_msg;
        ImageView iv_delete;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_down:
                if (popupWindow == null) {
                    popupWindow = new PopupWindow(MainActivity.this);
                    popupWindow.setWidth(et_input.getWidth());
                    int height = DensityUtil.dip2px(MainActivity.this,200);
                    popupWindow.setHeight(height);

                    popupWindow.setContentView(listView);
                    popupWindow.setFocusable(true);
                }
                popupWindow.showAsDropDown(et_input,0,0);

                break;
        }
    }

}
