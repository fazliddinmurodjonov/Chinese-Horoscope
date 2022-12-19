package com.programmsoft.utils

import android.annotation.SuppressLint
import com.programmsoft.horoscope.MainActivity
import com.programmsoft.models.ConstellationsAPI
import com.programmsoft.room.entity.ConstellationsBase
import com.programmsoft.room.entity.ConstellationsLoveBase
import com.programmsoft.retrofit.Common
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
            .enqueue(object : Callback<List<ConstellationsAPI>> {
                override fun onResponse(
                    call: Call<List<ConstellationsAPI>>,
                    response: Response<List<ConstellationsAPI>>,
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        SharedPreference.updateDB = 1
                        val body = response.body()
                        SharedPreference.updateDBTime = getDate(0)
                        body?.forEach { c ->
                            var constellations = ConstellationsBase()
                            constellations.name = c.name
                            constellations.todayUzb = c.today_uzb
                            constellations.todayKirill = c.today_kirill
                            constellations.todayRussian = c.today_ru
                            constellations.tomorrowUzb = c.tomorrow_uzb
                            constellations.tomorrowKirill = c.tomorrow_kirill
                            constellations.tomorrowRussian = c.tomorrow_ru
                            constellations.dateToday = getDate(0)
                            constellations.dateTomorrow = getDate(1)
                            constellations.workToday = MainActivity.percentageList[c.id.toInt()-1].todayWork
                            constellations.healthToday = MainActivity.percentageList[c.id.toInt()-1].todayHealth
                            constellations.loveToday = MainActivity.percentageList[c.id.toInt()-1].todayLove
                            constellations.workTomorrow = MainActivity.percentageList[c.id.toInt()-1].tomorrowWork
                            constellations.healthTomorrow = MainActivity.percentageList[c.id.toInt()-1].tomorrowHealth
                            constellations.loveTomorrow = MainActivity.percentageList[c.id.toInt()-1].tomorrowLove
                            db.constellationsBaseDao().insert(constellations)
                        }

                    }
                }

                override fun onFailure(call: Call<List<ConstellationsAPI>>, t: Throwable) {
                    SharedPreference.updateDB = 0
                }
            })

        retrofitService.getHoroscope(paramSecond)
            .enqueue(object : Callback<List<ConstellationsAPI>> {
                override fun onResponse(
                    call: Call<List<ConstellationsAPI>>,
                    response: Response<List<ConstellationsAPI>>,
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        SharedPreference.updateDB = 1
                        val body = response.body()
                        SharedPreference.updateDBTime = getDate(0)
                        body?.forEach { c ->
                            var constellationsLove = ConstellationsLoveBase()
                            constellationsLove.name = c.name
                            constellationsLove.todayUzb = c.today_uzb
                            constellationsLove.todayKirill = c.today_kirill
                            constellationsLove.todayRussian = c.today_ru
                            constellationsLove.tomorrowUzb = c.tomorrow_uzb
                            constellationsLove.tomorrowKirill = c.tomorrow_kirill
                            constellationsLove.tomorrowRussian = c.tomorrow_ru
                            constellationsLove.dateToday = getDate(0)
                            constellationsLove.dateTomorrow = getDate(1)
                            constellationsLove.workToday = MainActivity.percentageList[c.id.toInt()-1].todayWork
                            constellationsLove.healthToday = MainActivity.percentageList[c.id.toInt()-1].todayHealth
                            constellationsLove.loveToday = MainActivity.percentageList[c.id.toInt()-1].todayLove
                            constellationsLove.workTomorrow = MainActivity.percentageList[c.id.toInt()-1].tomorrowWork
                            constellationsLove.healthTomorrow = MainActivity.percentageList[c.id.toInt()-1].tomorrowHealth
                            constellationsLove.loveTomorrow = MainActivity.percentageList[c.id.toInt()-1].tomorrowLove
                            db.constellationsLoveBaseDao().insert(constellationsLove)
                        }
                    }
                }

                override fun onFailure(call: Call<List<ConstellationsAPI>>, t: Throwable) {
                    SharedPreference.updateDB = 0
                }
            })
    }

    private fun getRandomNumber(): Int {
        return Random.nextInt(65..85)
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