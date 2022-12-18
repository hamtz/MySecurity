package com.ilham.mysecurity


import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class PushNotificationService : FirebaseMessagingService() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val title = remoteMessage.notification!!.title
        val text = remoteMessage.notification!!.body
        val CHANNEL_ID = "Notifikasi Alert"
        val channel = NotificationChannel(CHANNEL_ID,"Heads Up Notification",NotificationManager.IMPORTANCE_HIGH
        )

        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
//        val notification: Notification.Builder =
//            Notification.Builder(this, CHANNEL_ID)
//                .setContentTitle(title)
//                .setContentText(text)
//                .setSmallIcon(R.drawable.ic_launcher_background).setAutoCancel(true)

//        val intent = Intent(this,MainActivity::class.java)
//        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        var notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_stat_ic_notification)
            .setContentTitle(title)
            .setContentText(text)
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setContentIntent(pendingIntent)
            .setVisibility(NotificationCompat.VISIBILITY_SECRET)
            .setAutoCancel(true)

        NotificationManagerCompat.from(this).notify(1, notification.build())

        super.onMessageReceived(remoteMessage)
    }

}




