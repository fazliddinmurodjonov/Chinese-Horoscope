package com.retrofit

object Common {
    const val HOROSCOPE_URL = "http://www.gor.uz/"
    val retrofitService: RetrofitService
        get() = RetrofitClient.getRetrofit(HOROSCOPE_URL).create(RetrofitService::class.java)
}