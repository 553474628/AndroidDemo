package com.example.sendnote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class SmsActivity extends AppCompatActivity {

    ListView lv;

    String[] msg = {"我在吃饭，请稍后联系","我在约会","我在学习","我在想你","我在打代码","我在睡觉","我在打电话"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        lv = (ListView) findViewById(R.id.lv);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(),R.layout.sms_item,msg);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String sms = msg[position];
                Intent intent = new Intent();
                intent.putExtra("sms",sms);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

}
