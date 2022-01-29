package com.reasonslab.btctracker.domain

import com.reasonslab.btctracker.data.model.GetPriceResponse
import com.reasonslab.btctracker.domain.repository.CoinsRepository
import com.reasonslab.btctracker.domain.usecase.BaseUseCaseCoroutine
import com.reasonslab.btctracker.domain.usecase.DefParams
import java.lang.Exception

class GetCoinPriceUseCase(private val coinsRepository: CoinsRepository) :
    BaseUseCaseCoroutine<GetCoinPriceParams, GetPriceResponse> {

    override suspend fun execute(
        params: GetCoinPriceParams,
        success: (GetPriceResponse) -> Unit,
        fail: (Exception) -> Unit
    ) {
        coinsRepository.getCoinPrice(params.coin, success, fail)
    }

}

data class GetCoinPriceParams(val coin: String) : DefParams()