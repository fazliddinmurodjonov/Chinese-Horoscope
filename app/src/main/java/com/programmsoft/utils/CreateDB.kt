package com.programmsoft.utils

import com.programmsoft.room.database.ChineseHoroscopeDB


object CreateDB {
    val db = ChineseHoroscopeDB.getInstance(App.instance)
}