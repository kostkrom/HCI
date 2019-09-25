package com.krom.moneymanager.app;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.krom.moneymanager.R;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle("MoneyManager")
                .setContentText("Add Today Expenses")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);



        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //notificationManager.notify();
        notificationManager.notify(001, builder.build());

    }
}
