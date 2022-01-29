package com.reasonslab.btctracker.domain.repository

import com.reasonslab.btctracker.data.model.GetPriceResponse
import com.reasonslab.btctracker.data.model.Rate

interface CoinsRepository {
    suspend fun getCoinPrice(currency: String,
                             success: (GetPriceResponse) -> Unit,
                             fail: (Exception) -> Unit)
    suspend fun getCachedCoinPrice(currency: String): Rate?

    suspend fun saveLimits(min: String,max: String)

    fun getLimits(): Pair<String,String>
}