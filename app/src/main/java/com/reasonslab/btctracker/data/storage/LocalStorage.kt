package com.reasonslab.btctracker.data.storage

import com.reasonslab.btctracker.data.model.Rate

interface LocalStorage {
    fun saveRate(rateItem: Rate)
    fun getRate(code: String): Rate?
    fun saveLimits(min: String, max: String)
    fun getLimits(): Pair<String, String>
}