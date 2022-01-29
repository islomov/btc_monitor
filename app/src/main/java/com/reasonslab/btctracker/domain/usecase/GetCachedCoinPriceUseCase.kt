package com.reasonslab.btctracker.domain.usecase

import com.reasonslab.btctracker.data.model.Rate
import com.reasonslab.btctracker.domain.repository.CoinsRepository

class GetCachedCoinPriceUseCase(private val repository: CoinsRepository) :
    BaseUseCase<GetCachedCoinParam, Rate?> {

    override suspend fun execute(params: GetCachedCoinParam): Rate? {
        return repository.getCachedCoinPrice(params.coin)
    }

}

data class GetCachedCoinParam(
    val coin: String
) : DefParams()