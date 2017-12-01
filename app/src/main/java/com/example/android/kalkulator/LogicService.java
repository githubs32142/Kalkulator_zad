package com.example.android.kalkulator;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class LogicService extends Service {
    private final IBinder mBinder= new LocalBinder();
    public LogicService() {
    }
    public Double add(Double n1,Double n2){
        return n1+n2;
    }
    public Double sub(Double n1,Double n2){
        return n1-n2;
    }
    public Double mul(Double n1,Double n2){
        return n1*n2;
    }
    public Double div(Double n1,Double n2){
        return n1/n2;
    }
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    public class LocalBinder extends Binder{
        LogicService getService(){
            return LogicService.this;
        }
    }
}
