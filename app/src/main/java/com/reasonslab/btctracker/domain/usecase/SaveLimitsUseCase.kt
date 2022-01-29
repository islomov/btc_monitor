package com.reasonslab.btctracker.domain.usecase

import com.reasonslab.btctracker.domain.repository.CoinsRepository

class SaveLimitsUseCase(private val repository: CoinsRepository) :
    BaseUseCase<SaveLimitsParams, Unit> {

    override suspend fun execute(params: SaveLimitsParams) {
        repository.saveLimits(params.min,params.max)
    }
}

data class SaveLimitsParams(val min: String, val max: String) : DefParams()