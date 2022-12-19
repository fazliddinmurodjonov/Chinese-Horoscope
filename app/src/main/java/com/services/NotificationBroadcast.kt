package com.programmsoft.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.programmsoft.chinesehoroscope.R
import com.programmsoft.chinesehoroscope.SplashActivity
import com.programmsoft.utils.App

const val notificationID = 1
const val channelID = "channel1"
const val titleExtra = "Horoscope"
const val messageExtra = "Horoscope"

class NotificationBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val i = Intent(context, SplashActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.notification_logo)
            .setContentTitle(context.getString(R.string.chinese_horoscope))
            .setContentText(context.getString(R.string.updateHoroscopeNotification))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(1, notification)
        }
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationID, notification)
    }
}