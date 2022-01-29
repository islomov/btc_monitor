package com.reasonslab.btctracker.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.reasonslab.btctracker.data.channel.Events
import com.reasonslab.btctracker.data.channel.GlobalEvent
import com.reasonslab.btctracker.data.model.Rate
import com.reasonslab.btctracker.data.notification.NotificationHelper
import com.reasonslab.btctracker.data.worker.PriceMonitorWorker
import com.reasonslab.btctracker.domain.GetCoinPriceParams
import com.reasonslab.btctracker.domain.GetCoinPriceUseCase
import com.reasonslab.btctracker.domain.usecase.*
import com.reasonslab.btctracker.utils.BTC
import com.reasonslab.btctracker.utils.USD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinApiExtension
import java.util.concurrent.TimeUnit

class RateViewModel(
    private val getCoinPriceUseCase: GetCoinPriceUseCase,
    private val getCachedCoinPriceUseCase: GetCachedCoinPriceUseCase,
    private val getLimitsUseCase: GetLimitsUseCase,
    private val saveLimitsUseCase: SaveLimitsUseCase
) : ViewModel() {
    // TODO: replace with flow
    private val _uiEvent: MutableLiveData<RateViewState> = MutableLiveData<RateViewState>()
    val uiEvent: LiveData<RateViewState> = _uiEvent

    fun getCachedPrice() {
        viewModelScope.launch {
            val rate = getCachedCoinPriceUseCase.execute(GetCachedCoinParam(USD))
            if (rate != null) {
                withContext(Dispatchers.Main) {
                    _uiEvent.value = (RateViewState.RateViewSuccess(rate))
                }
            }
            _uiEvent.postValue(RateViewState.RateViewProgress)
            getCoinPriceUseCase.execute(GetCoinPriceParams(BTC),
                success = { response ->
                    _uiEvent.postValue(RateViewState.RateViewSuccess(response.getRateWithLastUpdate()))
                }, fail = {
                    _uiEvent.postValue(RateViewState.RateViewFail(it.message))
                })
        }
    }

    fun saveLimit(min: String, max: String) {
        viewModelScope.launch {
            saveLimitsUseCase.execute(SaveLimitsParams(min, max))
        }
    }

    fun getCurrentPrice() {
        viewModelScope.launch {
            _uiEvent.postValue(RateViewState.RateViewProgress)
            getCoinPriceUseCase.execute(GetCoinPriceParams(BTC),
                success = { response ->
                    val rate = response.getRateWithLastUpdate()
                    _uiEvent.postValue(RateViewState.RateViewSuccess(rate))
                }, fail = {
                    _uiEvent.postValue(RateViewState.RateViewFail(it.message))
                })
        }
    }
    // Worker is a good choice for background tasks. Cuz it takes into accaunt Android Stanby,Doze modes,locks.
    // But worker is not a good solution for BTC price monitoring. Cuz BTC price is changed each seconds.
    // It would be nice to use WebSockets ws:// or graphql.
    fun mointorPrice(appContext: Context) {
        //TODO: Add constraints for worker
        WorkManager.getInstance(appContext)
            .enqueueUniquePeriodicWork(
                "btc-price-monitor",
                ExistingPeriodicWorkPolicy.KEEP,
                PeriodicWorkRequestBuilder<PriceMonitorWorker>(15, TimeUnit.MINUTES).build())
        addMonitorChangeObserver()
    }

    private fun addMonitorChangeObserver() {
        GlobalEvent.add(btcPriceUpdateObserver)
    }

    private fun removeMonitorChangeObserver() {
        GlobalEvent.remove(btcPriceUpdateObserver)
    }

    private val btcPriceUpdateObserver = Observer<Events> { event ->
            if (event is Events.BTCPriceChangedEvent) {
                _uiEvent.postValue(RateViewState.RateViewSuccess(event.rate))
            }
        }

    override fun onCleared() {
        removeMonitorChangeObserver()
        super.onCleared()
    }

}

sealed class RateViewState {
    data class RateViewSuccess(val rate: Rate) : RateViewState()
    data class RateViewFail(val msg: String?) : RateViewState()
    object RateViewProgress : RateViewState()
}