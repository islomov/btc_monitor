package com.reasonslab.btctracker.koin


val applicationComponent = listOf(
    networkModule,
    repositoryModule,
    storageModule,
    useCaseModule,
    viewModelModule,
    notificationModule
)