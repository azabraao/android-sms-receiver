package br.com.azabraao.leiturasms;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class LeitorSMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle myBundle = intent.getExtras();
        SmsMessage [] messages = null;
        String strMessage = "";

        if(myBundle != null) {
            Object[] pdus = (Object[]) myBundle.get("pdus");

            messages = new SmsMessage[pdus.length];

            for(int i = 0; i < messages.length; i++) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    String format = myBundle.getString("format");
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                } else {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }

                strMessage += "SMS From: " + messages[i].getDisplayOriginatingAddress();
                strMessage += " : ";
                strMessage += messages[i].getMessageBody();
                strMessage += "\n";
            }

            Toast.makeText(context, strMessage, Toast.LENGTH_LONG).show();
        }

/*
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.0) {
            NotificationChannel notificationChannel =
                    new NotificationChannel("alertas", "My Notifications", NotificationManager.IMPORTANCE_MAX);

            notificationChannel.setDescription("Channel desc");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notification =
                new NotificationCompat.Builder(this,"alertas");

        notification.setContentTitle("Alerta");
        notification.setChannelId("alertas");
        notification.setContentText("Poxa, não deu por hoje kk");
        notification.setSmallIcon(R.mipmap.ic_launcher);


        notificationManager.notify(100,notification.build());

        notification.setContentIntent(
                PendingIntent.getActivity(this,0,
                        new Intent(this, MainActivity.class),
                        PendingIntent.FLAG_UPDATE_CURRENT
                )
        );
        */
    }
}
