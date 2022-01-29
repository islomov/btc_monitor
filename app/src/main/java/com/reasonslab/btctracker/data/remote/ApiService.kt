package com.reasonslab.btctracker.data.remote

import com.reasonslab.btctracker.data.model.GetPriceResponse
import com.reasonslab.btctracker.utils.BTC
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("bpi/currentprice/{coin}")
    suspend fun getSpecificCoinPrice(@Path("coin") coin: String = BTC.lowercase()): GetPriceResponse

}