package com.retrofit

object Common {
    const val HOROSCOPE_URL = "http://programmsoft.uz/"
    val retrofitService: RetrofitService
        get() = RetrofitClient.getRetrofit(HOROSCOPE_URL).create(RetrofitService::class.java)
}