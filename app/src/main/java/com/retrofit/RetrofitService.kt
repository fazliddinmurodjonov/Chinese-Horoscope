package com.retrofit

import com.programmsoft.models.ZodiacAPI
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("app_api.php")
    fun getHoroscope(@Query("param") param: Int): Call<List<ZodiacAPI>>

}