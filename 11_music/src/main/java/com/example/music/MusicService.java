package com.example.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MusicService extends Service {
    String TAG = "MusicService";
    MediaPlayer mediaPlayer;
    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        mediaPlayer = new MediaPlayer();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void seekTo(int position) {
        mediaPlayer.seekTo(position);
    }

    public void playMusic(){
        try {
            mediaPlayer.reset();
            File file = new File(Environment.getExternalStorageDirectory(),"/Music/ahq.mp3");
            mediaPlayer.setDataSource(file.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            updateSeekBar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateSeekBar() {
        final int duration = mediaPlayer.getDuration();
        //使用Timer定时器去定时获取当前进度
        final Timer timer = new Timer();
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                int position = mediaPlayer.getCurrentPosition();
                Message message = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putInt("duration",duration);
                bundle.putInt("currentPosition",position);
                message.setData(bundle);
                MainActivity.handler.sendMessage(message);
            }
        };
        timer.schedule(task,100,1000);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                timer.cancel();
                task.cancel();
            }
        });
    }

    public void pauseMusic(){
        mediaPlayer.pause();
    }

    public void restartMusic(){
        mediaPlayer.start();
    }

    private class MyBinder extends Binder implements Iservice {

        @Override
        public void callPlayMusic() {
            playMusic();
        }

        @Override
        public void callPauseMusic() {
            pauseMusic();
        }

        @Override
        public void callRestarstMusic() {
            restartMusic();
        }

        @Override
        public void callSeekTo(int position) {
            seekTo(position);
        }

    }
}
