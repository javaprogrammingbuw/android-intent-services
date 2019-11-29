package com.nana.intent_service_example;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

public class ReminderService extends IntentService {

    public static final String TAG = "ReminderService";

    public ReminderService(String name) {
        super(name);
    }

    public ReminderService(){this("ReminderService");}

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Bundle bundle = intent.getExtras();
        int duration = bundle.getInt("duration");
        try{
            Thread.sleep(duration*1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        Log.d(TAG, "Time is up");
        MainActivity.INSTANCE.showNotification("Time is up!");
        Messenger messenger = (Messenger) bundle.get("messenger");
        Message message = Message.obtain();
        message.setData(bundle);
        try{
            messenger.send(message);
        } catch(RemoteException e) {
            e.printStackTrace();
        }
    }
}
