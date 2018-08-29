package com.example.zzanzu.serviceexample;

import android.os.Handler;

public class ServiceThread extends Thread {
    Handler handler;
    boolean isRun = true;

    public ServiceThread(Handler handler) {
        this.handler = handler;
    }

    public void stopForever() {
        synchronized (this) {
            this.isRun = false;
        }
    }

    public void run() {
        // 반복적으로 수행할 작업
        while(isRun) {
            handler.sendEmptyMessage(0);

            try {
                Thread.sleep(10000);
            } catch (Exception e) {}
        }
    }
}
