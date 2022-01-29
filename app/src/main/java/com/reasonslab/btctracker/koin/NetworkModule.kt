package com.reasonslab.btctracker.koin

import com.reasonslab.btctracker.data.remote.ApiService
import com.reasonslab.btctracker.data.remote.BaseUrlEnum
import okhttp3.OkHttpClient
import org.koin.android.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import org.koin.dsl.module

val networkModule = module {

    single {
        apiService(url(),httpClient())
    }

}

fun url() = if(BuildConfig.DEBUG) BaseUrlEnum.DEV_SERVER.link else BaseUrlEnum.PROD_SERVER.link

fun httpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.MILLISECONDS)
        .writeTimeout(CONNECTION_TIME_OUT, TimeUnit.MILLISECONDS)
        .readTimeout(CONNECTION_TIME_OUT, TimeUnit.MILLISECONDS).build()


fun apiService(url: String, client: OkHttpClient): ApiService {

    return Retrofit.Builder()
        .baseUrl(url)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

}

private const val CONNECTION_TIME_OUT = 10000L