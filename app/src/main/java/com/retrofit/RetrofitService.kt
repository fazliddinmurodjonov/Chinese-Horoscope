package com.retrofit

import com.programmsoft.models.ZodiacAPI
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("muchal_content.php")
    fun getHoroscope(): Call<List<ZodiacAPI>>

}