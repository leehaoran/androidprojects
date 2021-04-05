package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MyTest";
    private IDemoInterface demoInterface = null;
    private Button bindService;
    private Button unbindService;
    private TextView textView;

    private ServiceConnection serviceConnect = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            demoInterface = IDemoInterface.Stub.asInterface(service);
            if (demoInterface == null) {
                Log.e(TAG, " null service");
            } else {
                Log.d(TAG, " bind service");
                try {
                    String result = demoInterface.getResult("demo");
                    textView.setText(result);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, " service disconnected");
            demoInterface = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActivity();
    }

    private void initActivity() {
        bindService = findViewById(R.id.bind);
        unbindService = findViewById(R.id.unbind);
        textView = findViewById(R.id.textView);
        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setAction("com.example.myapplication.DemoService");
        intent.setPackage("com.example.myapplication");
        bindService(intent, serviceConnect, BIND_AUTO_CREATE);
        Log.i(TAG, "start bind service");
    }

    private void unbindService() {
        unbindService(serviceConnect);
        Log.i(TAG, "unbind service");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bind:
                bindService();
                break;
            case R.id.unbind:
                unbindService();
                break;
            default:
                break;
        }
    }
}
