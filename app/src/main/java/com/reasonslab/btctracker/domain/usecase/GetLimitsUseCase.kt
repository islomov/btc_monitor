package com.reasonslab.btctracker.domain.usecase

import com.reasonslab.btctracker.domain.repository.CoinsRepository

class GetLimitsUseCase(private val coinsRepository: CoinsRepository) :
    BaseUseCaseNoCoroutine<DefParams, Pair<String, String>> {
    override fun execute(params: DefParams): Pair<String, String> {
        return coinsRepository.getLimits()

    }


}