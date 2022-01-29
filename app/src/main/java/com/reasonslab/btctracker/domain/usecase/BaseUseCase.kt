package com.reasonslab.btctracker.domain.usecase

import java.lang.Exception

interface BaseUseCase<P : DefParams, T> {

    suspend fun execute(params: P): T
}

interface BaseUseCaseNoCoroutine<P : DefParams, T> {

    fun execute(params: P): T
}
// TODO: Avoid using callbacks for useCases(success,fail), this makes code more unreadable.
// Check Wrapper patterns, Like Either<out L, out R>, which will return from suspend function,
// Either object which contains Error and Success objects.

interface BaseUseCaseCoroutine<P : DefParams, T> {

    suspend fun execute(params: P, success: (T) -> Unit, fail: (Exception) -> Unit)
}

open class DefParams