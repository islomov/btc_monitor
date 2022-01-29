package com.reasonslab.btctracker.koin

import android.content.Context
import com.reasonslab.btctracker.data.notification.NotificationHelper
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val notificationModule = module {
    single {
        createNotification(androidApplication())
    }
}

fun createNotification(context: Context): NotificationHelper {
    return NotificationHelper(context)
}