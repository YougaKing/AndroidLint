package com.paincker.lint.demo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "toast", Toast.LENGTH_SHORT);

        callNewApi();

        Log.d("tag", "msg");

        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).run();

        Environment.getExternalStorageDirectory();

        try {
            ArrayList<NetworkInterface> networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface networkInterface : networkInterfaces) {
                networkInterface.getHardwareAddress();
            }

        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    private void callNewApi() {
        new View(this).setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

            }
        });
    }
}
