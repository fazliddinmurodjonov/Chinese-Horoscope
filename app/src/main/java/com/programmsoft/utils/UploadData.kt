package com.programmsoft.utils

import android.annotation.SuppressLint
import com.programmsoft.chinesehoroscope.MainActivity
import com.programmsoft.models.ZodiacAPI
import com.programmsoft.room.entity.ZodiacBase
import com.retrofit.Common
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random
import kotlin.random.nextInt

object UploadData {
    fun uploadHoroscope(paramFirst: Int, paramSecond: Int) {
        val retrofitService = Common.retrofitService
        SharedPreference.init(App.instance)
        val db = CreateDB.db
        retrofitService.getHoroscope(paramFirst)
            .enqueue(object : Callback<List<ZodiacAPI>> {
                override fun onResponse(
                    call: Call<List<ZodiacAPI>>,
                    response: Response<List<ZodiacAPI>>,
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        SharedPreference.updateDB = 1
                        val body = response.body()
                        SharedPreference.updateDBTime = getDate(0)
                        body?.forEach { z ->
                            var zodiac = ZodiacBase()
                            zodiac.name = z.name
                            zodiac.todayUzb = z.today_uzb
                            zodiac.tomorrowUzb = z.tomorrow_uzb
                            zodiac.dateToday = getDate(0)
                            zodiac.dateTomorrow = getDate(1)
                            zodiac.workToday = MainActivity.percentageList[z.id -1].todayWork
                            zodiac.healthToday = MainActivity.percentageList[z.id -1].todayHealth
                            zodiac.loveToday = MainActivity.percentageList[z.id -1].todayLove
                            zodiac.workTomorrow = MainActivity.percentageList[z.id -1].tomorrowWork
                            zodiac.healthTomorrow = MainActivity.percentageList[z.id -1].tomorrowHealth
                            zodiac.loveTomorrow = MainActivity.percentageList[z.id -1].tomorrowLove
                            db.zodiacBaseDao().insert(zodiac)
                        }

                    }
                }

                override fun onFailure(call: Call<List<ZodiacAPI>>, t: Throwable) {
                    SharedPreference.updateDB = 0
                }
            })


    }



    @SuppressLint("NewApi")
    fun getDate(day: Int): String {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val date = LocalDateTime.now()
        val today = date.format(formatter)
        val tomorrow = date.plusDays(1).format(formatter)
        var d = today
        if (day == 1) {
            d = tomorrow
        }
        return d
    }

}