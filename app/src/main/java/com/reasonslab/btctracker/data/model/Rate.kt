package com.reasonslab.btctracker.data.model

import com.google.gson.annotations.SerializedName

data class Rate(
    @SerializedName("code")
    val code: String,
    @SerializedName("rate")
    val rate: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("rate_float")
    val rate_float: Float
) {
    var lastUpdate: String = ""
}