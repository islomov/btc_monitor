package com.reasonslab.btctracker.data.storage

import android.content.SharedPreferences
import com.reasonslab.btctracker.data.model.Rate

class LocalStorageImpl(val preference: SharedPreferences) : LocalStorage {

    override fun saveRate(rateItem: Rate) {
        preference.edit().apply {
            putString("${rateItem.code.lowercase()}_$RATE_DESC", rateItem.description)
            putString("${rateItem.code.lowercase()}_$RATE_STR", rateItem.rate)
            putFloat("${rateItem.code.lowercase()}_$RATE_FLT", rateItem.rate_float)
            putString("${rateItem.code.lowercase()}_$RATE_UPDT", rateItem.lastUpdate)
        }.apply()
    }

    override fun getRate(code: String): Rate? {
        val desc = preference.getString("${code.lowercase()}_$RATE_DESC", "") ?: ""
        val rateStr = preference.getString("${code.lowercase()}_$RATE_STR", "") ?: ""
        val rateFloat = preference.getFloat("${code.lowercase()}_$RATE_FLT", 0f) ?: 0f
        val lastUpdate = preference.getString("${code.lowercase()}_$RATE_UPDT", "") ?: ""
        if (desc.isEmpty() || rateStr.isEmpty() || rateFloat == 0f) {
            return null
        }
        val rate = Rate(code, rateStr, desc, rateFloat)
        rate.lastUpdate = lastUpdate
        return rate
    }

    override fun saveLimits(min: String, max: String) {
        preference.edit().apply {
            putString("min", min)
            putString("max", max)
        }.apply()
    }

    override fun getLimits(): Pair<String, String> {
        val min = preference.getString("min", "") ?: ""
        val max = preference.getString("max", "") ?: ""
        return Pair(min, max)
    }


    companion object {
        const val RATE_DESC = "rate_desc"
        const val RATE_STR = "rate_str"
        const val RATE_FLT = "rate_flt"
        const val RATE_UPDT = "rate_updt"
    }
}