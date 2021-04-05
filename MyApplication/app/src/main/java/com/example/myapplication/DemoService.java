package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class DemoService extends Service {
    private static final String TAG = DemoService.class.getName();

    private IBinder stub = new IDemoInterface.Stub() {
        @Override
        public String getResult(String name) throws RemoteException {
            return name + " from aidl";
        }
    };

    public DemoService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return stub;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }
}
