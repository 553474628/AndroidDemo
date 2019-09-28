package com.example.music;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Bundle data = msg.getData();
            int duration = data.getInt("duration");
            int position = data.getInt("currentPosition");
            seekBar.setMax(duration);
            seekBar.setProgress(position);
            return false;
        }
    });

    Button bt_play;
    Button bt_pause;
    Button bt_restart;
    private static SeekBar seekBar;

    Iservice iservice;
    MyConnection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_play = (Button) findViewById(R.id.bt_play);
        bt_pause = (Button) findViewById(R.id.bt_pause);
        bt_restart = (Button) findViewById(R.id.bt_restart);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        bt_play.setOnClickListener(this);
        bt_pause.setOnClickListener(this);
        bt_restart.setOnClickListener(this);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                iservice.callSeekTo(seekBar.getProgress());
            }
        });

        Intent intent = new Intent(this,MusicService.class);
        startService(intent);
        connection = new MyConnection();
        bindService(intent,connection,BIND_AUTO_CREATE);
    }

    private class MyConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
            iservice = (Iservice) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_play:
                iservice.callPlayMusic();
                break;
            case R.id.bt_pause:
                iservice.callPauseMusic();
                break;
            case R.id.bt_restart:
                iservice.callRestarstMusic();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(connection);
        super.onDestroy();
    }
}
