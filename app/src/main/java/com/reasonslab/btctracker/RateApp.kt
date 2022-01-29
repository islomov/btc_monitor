package com.reasonslab.btctracker

import android.app.Application
import com.reasonslab.btctracker.koin.applicationComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RateApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setUpKoin()
    }


    fun setUpKoin() {
        startKoin {
            androidContext(this@RateApp)
            modules(applicationComponent)
        }
    }
}