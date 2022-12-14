package com.programmsoft.utils

import android.annotation.SuppressLint
import com.programmsoft.models.Percentages
import com.programmsoft.models.ZodiacAPI
import com.retrofit.Common
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random
import kotlin.random.nextInt

object UpdateData {
    var percentageList: ArrayList<Percentages> = percentagesList()

    fun updateHoroscope() {
        SharedPreference.init(App.instance)
        val lang = LanguageManager(App.instance)
        lang.updateResource(SharedPreference.lang!!)
        val retrofitService = Common.retrofitService
        val db = CreateDB.db
        retrofitService.getHoroscope()
            .enqueue(object : Callback<List<ZodiacAPI>> {
                override fun onResponse(
                    call: Call<List<ZodiacAPI>>,
                    response: Response<List<ZodiacAPI>>,
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        SharedPreference.updateDB = 1
                        val body = response.body()
                        body?.forEach { c ->
                            var zodiacId =
                                db.zodiacBaseDao().getById(c.id.toInt())
                            zodiacId.todayUzb = c.today_uzb
                            zodiacId.tomorrowUzb = c.tomorrow_uzb
                            zodiacId.dateToday = getDate(0)
                            zodiacId.dateTomorrow = getDate(1)
                            zodiacId.workToday = zodiacId.workTomorrow
                            zodiacId.healthToday = zodiacId.healthTomorrow
                            zodiacId.loveToday = zodiacId.loveTomorrow
                            zodiacId.workTomorrow =
                                percentageList[c.id.toInt() - 1].tomorrowWork
                            zodiacId.healthTomorrow =
                                percentageList[c.id.toInt()- 1].tomorrowHealth
                            zodiacId.loveTomorrow =
                                percentageList[c.id.toInt() - 1].tomorrowLove
                            db.zodiacBaseDao().update(zodiacId)
                        }
                    }
                }

                override fun onFailure(call: Call<List<ZodiacAPI>>, t: Throwable) {
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


    private fun percentagesList(): ArrayList<Percentages> {
        var list = ArrayList<Percentages>()
        for (i in 0 until 12) {
            val percentages = Percentages(getRandomNumber(),
                getRandomNumber(),
                getRandomNumber(),
                getRandomNumber(),
                getRandomNumber(),
                getRandomNumber())
            list.add(percentages)
        }
        return list
    }

}