package com.example.imagelook;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private EditText et_path;
    private ImageView iv;
    private Button bt_look;
//    private MyHandler handler;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Bitmap bitmap = (Bitmap) msg.obj;
            iv.setImageBitmap(bitmap);
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_path = (EditText) findViewById(R.id.et_path);
        iv = (ImageView) findViewById(R.id.iv);
        bt_look = (Button) findViewById(R.id.bt_look);

        bt_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            String path = et_path.getText().toString().trim();
                            File file = new File(getCacheDir(), Base64.encodeToString(path.getBytes(),Base64.DEFAULT));
                            if (file.exists() && file.length() > 0) {
                                Log.e(TAG, "使用缓存图片", null);
                                Bitmap cachemap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                Message message = Message.obtain();
                                message.obj = cachemap;
                                handler.sendMessage(message);
                            } else {
                                Log.e(TAG, "第一次访问网络", null);
                                URL url = new URL(path);
                                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                connection.setRequestMethod("GET");
                                connection.setConnectTimeout(5000);
                                int code = connection.getResponseCode();
                                if (code == 200) {
                                    InputStream inputStream = connection.getInputStream();

                                    //实现图片的缓存
                                    FileOutputStream fos = new FileOutputStream(file);
                                    int len = -1;
                                    byte[] buffer = new byte[1024];
                                    while ((len = inputStream.read(buffer)) != -1) {
                                        fos.write(buffer, 0, len);
                                    }
                                    fos.close();
                                    inputStream.close();

                                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                    Message message = Message.obtain();
                                    message.obj = bitmap;
                                    handler.sendMessage(message);
                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

            }
        });
    }

    /*static class MyHandler extends Handler{
        private WeakReference<Activity> weakReference;

        public MyHandler(Activity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Bitmap bitmap = (Bitmap) msg.obj;
            iv.setImageBitmap(bitmap);
        }
    }*/
}
