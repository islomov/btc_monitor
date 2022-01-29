package com.reasonslab.btctracker.koin

import com.reasonslab.btctracker.domain.repository.CoinsRepository
import com.reasonslab.btctracker.domain.GetCoinPriceUseCase
import com.reasonslab.btctracker.domain.usecase.GetCachedCoinPriceUseCase
import com.reasonslab.btctracker.domain.usecase.GetLimitsUseCase
import com.reasonslab.btctracker.domain.usecase.SaveLimitsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory {
        getCoinsPriceUseCase(get())
    }
    factory {
        getCachedCoinsPriceUseCase(get())
    }
    factory {
        getLimitUseCase(get())
    }
    factory {
        saveLimitUseCase(get())
    }
}

fun getCoinsPriceUseCase(repository: CoinsRepository): GetCoinPriceUseCase {
    return GetCoinPriceUseCase(repository)
}

fun getCachedCoinsPriceUseCase(repository: CoinsRepository): GetCachedCoinPriceUseCase {
    return GetCachedCoinPriceUseCase(repository)
}

fun getLimitUseCase(repository: CoinsRepository): GetLimitsUseCase {
    return GetLimitsUseCase(repository)
}

fun saveLimitUseCase(repository: CoinsRepository): SaveLimitsUseCase {
    return SaveLimitsUseCase(repository)
}