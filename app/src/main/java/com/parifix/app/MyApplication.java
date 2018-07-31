package com.parifix.app;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;

import com.onesignal.OneSignal;
import com.pixplicity.easyprefs.library.Prefs;

public class MyApplication extends Application {


    private static MyApplication singleton;
    private static Context mContext;

    public static MyApplication getInstance(){
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;

        initOneSignal();
        initPref();

    }

    private void initOneSignal() {
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }

    private void initPref(){
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
    }



}
