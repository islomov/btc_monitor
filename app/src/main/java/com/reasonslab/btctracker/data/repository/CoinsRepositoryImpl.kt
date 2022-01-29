package com.reasonslab.btctracker.data.repository

import com.reasonslab.btctracker.data.model.GetPriceResponse
import com.reasonslab.btctracker.data.model.Rate
import com.reasonslab.btctracker.data.remote.ApiService
import com.reasonslab.btctracker.domain.repository.CoinsRepository
import com.reasonslab.btctracker.data.storage.LocalStorage

class CoinsRepositoryImpl(val apiService: ApiService, val storage: LocalStorage) : CoinsRepository {

    override suspend fun getCoinPrice(
        currency: String,
        success: (GetPriceResponse) -> Unit,
        fail: (Exception) -> Unit
    ) {
        try {
            val response = apiService.getSpecificCoinPrice(currency)
            val rate = response.getRateWithLastUpdate()
            storage.saveRate(rate)
            success.invoke(response)
            //TODO: don't write general exceptions, handle API errors and local errors(NoInternet)
        } catch (e: Exception) {
            fail.invoke(e)
        }
    }

    override suspend fun getCachedCoinPrice(currency: String): Rate? {
        return storage.getRate(currency)
    }

    override suspend fun saveLimits(min: String, max: String) {
        storage.saveLimits(min, max)
    }

    override fun getLimits(): Pair<String, String> {
        return storage.getLimits()
    }

}