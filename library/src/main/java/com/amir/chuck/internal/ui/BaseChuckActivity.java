package com.amir.chuck.internal.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amir.chuck.internal.support.NotificationHelper;

public abstract class BaseChuckActivity extends AppCompatActivity {

    private static boolean inForeground;

    private NotificationHelper notificationHelper;

    public static boolean isInForeground() {
        return inForeground;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationHelper = new NotificationHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        inForeground = true;
        notificationHelper.dismiss();
    }

    @Override
    protected void onPause() {
        super.onPause();
        inForeground = false;
    }

}
