package com.example.sendnote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.nio.file.attribute.PosixFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    ListView lv;
    List<Person> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        lv = (ListView) findViewById(R.id.lv);

        list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Person person = new Person();
            person.setName("何凯阳"+i);
            person.setPhone("1577863577"+i);
            list.add(person);
        }
        lv.setAdapter(new MyAdapter());

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String phone = list.get(position).getPhone();
                Intent intent = new Intent();
                intent.putExtra("phone",phone);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    private class MyAdapter extends BaseAdapter{  //ArrayAdapter

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder viewHolder;
            if (convertView == null) {
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.contact_item,parent,false);
                viewHolder = new ViewHolder();
                viewHolder.name = view.findViewById(R.id.tv_name);
                viewHolder.phone = view.findViewById(R.id.tv_phone);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            Person person = list.get(position);
            viewHolder.name.setText(person.getName());
            viewHolder.phone.setText(person.getPhone());
            return view;
        }

        class ViewHolder{
            TextView name;
            TextView phone;
        }
    }
}
