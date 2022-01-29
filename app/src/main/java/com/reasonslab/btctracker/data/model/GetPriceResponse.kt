package com.reasonslab.btctracker.data.model

import com.google.gson.annotations.SerializedName

data class GetPriceResponse(
    @SerializedName("time")
    val time: TimeItem,
    @SerializedName("disclaimer")
    val disclaimer: String,
    @SerializedName("bpi")
    val bpi: BpiRateItem
) {

    data class TimeItem(
        @SerializedName("updated")
        val updated: String
    )

    data class BpiRateItem(
        @SerializedName("USD")
        val usd: Rate,
        @SerializedName("BTC")
        val btc: Rate
    )

    fun getRateWithLastUpdate(): Rate {
        val time = time.updated
        bpi.usd.lastUpdate = time
        return bpi.usd
    }
}