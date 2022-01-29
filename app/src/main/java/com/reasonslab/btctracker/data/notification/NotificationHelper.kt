package com.reasonslab.btctracker.data.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.reasonslab.btctracker.R
import com.reasonslab.btctracker.ui.MainActivity

class NotificationHelper(private val context: Context) {

    fun show(action: String) {
        // TODO: Handle dynamic channels,now ChannelID is the same for all type of notifications.
        val channelId = CHANNEL_ID
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            showApi24Lower(action, channelId)
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
            && Build.VERSION.SDK_INT < Build.VERSION_CODES.O
        ) {
            showApi26Lower(action, channelId)
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(notificationManager, channelId)
        }

        val builder = NotificationCompat.Builder(context, channelId)
        builder.apply {
            setContentTitle("Bitcoin Alert")
            setContentText(action)
            setSmallIcon(R.drawable.ic_notification)
            color = ContextCompat.getColor(context, R.color.ui_purple)
            setWhen(System.currentTimeMillis())
            setVibrate(longArrayOf(1000, 1000))
            setGroup(CHANNEL_ID)
            setContentIntent(getPendingIntent(context, action, PUSH_ID))
            setAutoCancel(true)
            createGroup(channelId)
            // TODO: Handle dynamic id, now PUSH_ID is the same for all notifications
            notificationManager.notify(PUSH_ID, builder.build())
        }
    }

    private fun showApi26Lower(action: String, channelId: String) {

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(context, channelId)
        builder.apply {
            setContentTitle("BTC Alert")
            setContentText(action)
            setSmallIcon(R.drawable.ic_notification)
            color = ContextCompat.getColor(context, R.color.ui_purple)
            setWhen(System.currentTimeMillis())
            setVibrate(longArrayOf(1000, 1000))
            setGroup(CHANNEL_ID)
            setContentIntent(getPendingIntent(context, action, PUSH_ID))
            setAutoCancel(true)
            createGroup(channelId)
            notificationManager.notify(PUSH_ID, builder.build())
        }
    }


    /**
     * Works when api level < 24
     * */
    private fun showApi24Lower(action: String, channelId: String) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(context, channelId)
        builder.apply {
            setContentTitle("BTC Alert")
            setContentText(action)
            setSmallIcon(R.drawable.ic_notification)
            color = ContextCompat.getColor(context, R.color.ui_purple)
            setWhen(System.currentTimeMillis())
            setVibrate(longArrayOf(1000, 1000))
            setContentIntent(getPendingIntent(context, action, PUSH_ID))
            setAutoCancel(true)
            notificationManager.notify(PUSH_ID, builder.build())
        }
    }

    private fun createGroup(chanelId: String) {
        val builder = NotificationCompat.Builder(context, chanelId)
        builder.apply {
            color = ContextCompat.getColor(context, R.color.ui_purple)
            setSmallIcon(R.drawable.ic_notification)
            setContentInfo("BTC Tracker")
            setGroupSummary(true)
            setGroup(CHANNEL_ID)
        }
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(-10, builder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(
        notificationManager: NotificationManager,
        channelId: String
    ) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, "Bit tracker", importance)
        channel.enableVibration(true)
        channel.lightColor = ContextCompat.getColor(context, R.color.ui_purple)
        channel.enableLights(true)
        notificationManager.createNotificationChannel(channel)
    }

    // Notification TAP action
    private fun getPendingIntent(context: Context, action: String, uniqueId: Int): PendingIntent {
        val intentFilter = IntentFilter()
        intentFilter.addAction(action)
        val intent = Intent(context, MainActivity::class.java)
        intent.action = action
        intent.putExtras(prepareBundle(action))
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        return PendingIntent.getActivity(context, uniqueId, intent, PendingIntent.FLAG_ONE_SHOT)
    }

    private fun prepareBundle(action: String): Bundle {
        val bundle = Bundle()
        bundle.putString("action", action)
        return bundle
    }

    companion object {
        private const val CHANNEL_ID = "BASE_BIT_TRACKER"
        private const val PUSH_ID = 1111
    }

}