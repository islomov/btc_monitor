package com.reasonslab.btctracker.data.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.reasonslab.btctracker.data.channel.Events
import com.reasonslab.btctracker.data.channel.GlobalEvent
import com.reasonslab.btctracker.data.notification.NotificationHelper
import com.reasonslab.btctracker.domain.GetCoinPriceParams
import com.reasonslab.btctracker.domain.GetCoinPriceUseCase
import com.reasonslab.btctracker.domain.usecase.DefParams
import com.reasonslab.btctracker.domain.usecase.GetLimitsUseCase
import com.reasonslab.btctracker.utils.BTC
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.math.roundToInt

@KoinApiExtension
class PriceMonitorWorker(
    context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams), KoinComponent {

    private val getCoinsPriceUseCase: GetCoinPriceUseCase by inject()
    private val getLimitsUseCase: GetLimitsUseCase by inject()
    private val notificationHelper: NotificationHelper by inject()

    override suspend fun doWork(): Result {
        Log.i("Islomov","doWork:${System.currentTimeMillis()}")
        getCoinsPriceUseCase.execute(params = GetCoinPriceParams(BTC), success = {
            GlobalEvent.push(Events.BTCPriceChangedEvent(it.getRateWithLastUpdate()))
            val range = getLimitsUseCase.execute(DefParams())
            val rate = it.getRateWithLastUpdate()
            // Dont round, use BigDecimal, to handle floating point in the code.
            val min = (range.first.toFloatOrNull() ?: 0f ).roundToInt()
            val max = (range.second.toFloatOrNull() ?: 0f).roundToInt()
            val price = rate.rate_float.roundToInt()
            if (min >= price) {
                notificationHelper.show("BTC price hit minimum value")
            }
            if (max <= price) {
                notificationHelper.show("BTC Price hit maximum value")
            }
            return@execute
        }, fail = {
            // TODO: Handle Fail case
            return@execute
        })
        return Result.success()
    }

}