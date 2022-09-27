package com.amir.chuck.internal.support;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import android.util.LongSparseArray;

import com.amir.chuck.Chuck;
import com.amir.chuck.R;
import com.amir.chuck.internal.data.HttpTransaction;
import com.amir.chuck.internal.ui.BaseChuckActivity;

public class NotificationHelper {

    private static final int NOTIFICATION_ID = 1138;
    private static final int BUFFER_SIZE = 10;

    private static LongSparseArray<HttpTransaction> transactionBuffer = new LongSparseArray<>();
    private static int transactionCount;

    private Context context;
    private NotificationManager notificationManager;

    public static synchronized void clearBuffer() {
        transactionBuffer.clear();
        transactionCount = 0;
    }

    private static synchronized void addToBuffer(HttpTransaction transaction) {
        if (transaction.getStatus() == HttpTransaction.Status.Requested) {
            transactionCount++;
        }
        transactionBuffer.put(transaction.getId(), transaction);
        if (transactionBuffer.size() > BUFFER_SIZE) {
            transactionBuffer.removeAt(0);
        }
    }

    public NotificationHelper(Context context) {
        this.context = context;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public synchronized void show(HttpTransaction transaction) {
        int flag;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            flag = PendingIntent.FLAG_IMMUTABLE;
        } else {
            flag = PendingIntent.FLAG_UPDATE_CURRENT;
        }

        addToBuffer(transaction);
        if (!BaseChuckActivity.isInForeground()) {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context,context.getString(R.string.channelId))
                    .setContentIntent(PendingIntent.getActivity(context, 0, Chuck.getLaunchIntent(context), flag))
                    .setSmallIcon(R.drawable.chuck_ic_notification_white_24dp)
                    .setColor(context.getResources().getColor(R.color.chuck_colorPrimary))
                    .setContentTitle(context.getString(R.string.chuck_notification_title));
            NotificationCompat.InboxStyle inboxStyle =
                    new NotificationCompat.InboxStyle();
            int count = 0;
            for (int i = transactionBuffer.size() - 1; i >= 0; i--) {
                if (count < BUFFER_SIZE) {
                    if (count == 0) {
                        mBuilder.setContentText(transactionBuffer.valueAt(i).getNotificationText());
                    }
                    inboxStyle.addLine(transactionBuffer.valueAt(i).getNotificationText());
                }
                count++;
            }
            mBuilder.setAutoCancel(true);
            mBuilder.setStyle(inboxStyle);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mBuilder.setSubText(String.valueOf(transactionCount));
            } else {
                mBuilder.setNumber(transactionCount);
            }
            mBuilder.addAction(getClearAction(flag));
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(context.getString(R.string.channelId), "Chuck Interceptor Channel", NotificationManager.IMPORTANCE_LOW);
                manager.createNotificationChannel(channel);
            }
            notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        }
    }

    @NonNull
    private NotificationCompat.Action getClearAction(int flag) {
        CharSequence clearTitle = context.getString(R.string.chuck_clear);
        Intent deleteIntent = new Intent(context, ClearTransactionsService.class);
        PendingIntent intent = PendingIntent.getService(context, 11, deleteIntent, flag);
        return new NotificationCompat.Action(R.drawable.chuck_ic_delete_white_24dp,
                clearTitle, intent);
    }

    public void dismiss() {
        notificationManager.cancel(NOTIFICATION_ID);
    }
}
