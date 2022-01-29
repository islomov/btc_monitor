package com.reasonslab.btctracker.data.remote

enum class BaseUrlEnum (var link: String) {
    DEV_SERVER("https://api.coindesk.com/v1/"),
    PROD_SERVER("https://api.coindesk.com/v1/")
}