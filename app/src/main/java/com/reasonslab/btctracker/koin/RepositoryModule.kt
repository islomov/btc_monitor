package com.reasonslab.btctracker.koin

import com.reasonslab.btctracker.data.remote.ApiService
import com.reasonslab.btctracker.data.repository.CoinsRepositoryImpl
import com.reasonslab.btctracker.data.storage.LocalStorage
import com.reasonslab.btctracker.domain.repository.CoinsRepository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        coinsRepostiry(get(), get())
    }
}

fun coinsRepostiry(apiService: ApiService, storage: LocalStorage): CoinsRepository {
    return CoinsRepositoryImpl(apiService, storage)
}

