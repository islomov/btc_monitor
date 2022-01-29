package com.reasonslab.btctracker.koin

import com.reasonslab.btctracker.domain.GetCoinPriceUseCase
import com.reasonslab.btctracker.domain.usecase.GetCachedCoinPriceUseCase
import com.reasonslab.btctracker.domain.usecase.GetLimitsUseCase
import com.reasonslab.btctracker.domain.usecase.SaveLimitsUseCase
import com.reasonslab.btctracker.ui.RateViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single {
        createRateViewModel(get(), get(),get(),get())
    }
}

fun createRateViewModel(
    getCachedCoinPriceUseCase: GetCachedCoinPriceUseCase,
    getCoinPriceUseCase: GetCoinPriceUseCase,
    getLimitsUseCase: GetLimitsUseCase,
    saveLimitsUseCase: SaveLimitsUseCase
): RateViewModel {
    return RateViewModel(getCoinPriceUseCase, getCachedCoinPriceUseCase,getLimitsUseCase,saveLimitsUseCase)
}