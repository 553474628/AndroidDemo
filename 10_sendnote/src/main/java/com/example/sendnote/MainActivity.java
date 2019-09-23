package com.example.sendnote;

import android.content.Intent;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText et_number;
    Button bt;
    EditText et_content;
    Button bt_insert;
    Button bt_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_number = (EditText) findViewById(R.id.et_number);
        bt = (Button) findViewById(R.id.bt);
        et_content = (EditText) findViewById(R.id.et_content);
        bt_send = (Button) findViewById(R.id.bt_send);
        bt_insert = (Button) findViewById(R.id.bt_insert);

        bt.setOnClickListener(this);
        bt_insert.setOnClickListener(this);
        bt_send.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String phone = data.getStringExtra("phone");
                    et_number.setText(phone);
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    String sms = data.getStringExtra("sms");
                    et_content.setText(sms);
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt:
                Intent intent = new Intent(this,ContactActivity.class);
                //startActivity(intent);
                startActivityForResult(intent,1);
                break;
            case R.id.bt_insert:
                Intent intent1 = new Intent(this,SmsActivity.class);
                startActivityForResult(intent1,2);
                break;
            case R.id.bt_send:
                send(v);
                break;
            default:
                break;
        }
    }

    private void send(View view) {
        String number = et_number.getText().toString().trim();
        String content = et_content.getText().toString().trim();
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number,null,content,null,null);
    }
}
