package com.codeunite.paymyrch.FCM;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MyTokenSharedPreference {
    public static final String FIREBASE_CLOUD_MESSAGING = "fcm";
    public static final String SET_NOTIFY = "set_notify";
    private Context context;
    private SharedPreferences prefs;

    public MyTokenSharedPreference(Context context) {
        this.context = context;
        this.prefs = context.getSharedPreferences("fcm", 0);
    }

    public void saveNotificationSubscription(boolean value) {
        Editor edits = this.prefs.edit();
        edits.putBoolean(SET_NOTIFY, value);
        edits.apply();
    }

    public boolean hasUserSubscribeToNotification() {
        return this.prefs.getBoolean(SET_NOTIFY, false);
    }

    public void clearAllSubscriptions() {
        this.prefs.edit().clear().apply();
    }
}
