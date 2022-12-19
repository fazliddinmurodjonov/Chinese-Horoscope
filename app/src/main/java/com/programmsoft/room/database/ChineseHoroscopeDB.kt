package com.programmsoft.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.programmsoft.room.dao.ZodiacBaseDao
import com.programmsoft.room.entity.ZodiacBase

@Database(entities = [ZodiacBase::class], version = 1)
abstract class ChineseHoroscopeDB : RoomDatabase() {
    abstract fun zodiacBaseDao() : ZodiacBaseDao

    companion object {
        private var instance: ChineseHoroscopeDB? = null

        @Synchronized
        fun getInstance(context: Context): ChineseHoroscopeDB {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(context,
                        ChineseHoroscopeDB::class.java,
                        "chinese_horoscope_db")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
            }
            return instance!!
        }
    }
}