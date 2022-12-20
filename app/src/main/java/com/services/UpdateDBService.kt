package com.programmsoft.services

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.programmsoft.utils.App
import com.programmsoft.utils.PushNotification
import com.programmsoft.utils.SharedPreference
import com.programmsoft.utils.UpdateData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class UpdateDBService(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        updateDB()
        return Result.success()
    }

    private fun updateDB() {
        SharedPreference.init(App.instance)
        val getCurrentTime = getTime()
        if (getCurrentTime != SharedPreference.updateDBTime) {
            UpdateData.updateHoroscope()
            if (SharedPreference.notificationTurnOn == 1) {
                PushNotification.createNotificationChannel()
                PushNotification.scheduleNotification()
            }
            SharedPreference.updateDBTime = getCurrentTime
        }
    }

    private fun getTime(): String {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val date = LocalDateTime.now()
        return date.format(formatter)
    }
}
