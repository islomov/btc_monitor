package com.reasonslab.btctracker.koin

import android.content.Context
import android.content.SharedPreferences
import com.reasonslab.btctracker.data.storage.LocalStorage
import com.reasonslab.btctracker.data.storage.LocalStorageImpl
import com.reasonslab.btctracker.utils.RATE_APP
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val storageModule = module {
    single {
        localStorage(createSharedPreference(androidApplication()))
    }
}

fun createSharedPreference(context: Context) =
    context.getSharedPreferences(RATE_APP, Context.MODE_PRIVATE)


fun localStorage(preferences: SharedPreferences): LocalStorage {
    return LocalStorageImpl(preferences)
}