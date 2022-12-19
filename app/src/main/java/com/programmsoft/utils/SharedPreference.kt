package com.programmsoft.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPreference {
    private const val NAME = "HoroscopeApp"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var sharedPreference: SharedPreferences
    fun init(context: Context) {
        sharedPreference = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var uploadManager: Int?
        get() = sharedPreference.getInt("uploadManager", 0)
        set(value) = sharedPreference.edit {
            it.putInt("Mode", value!!)
        }

    var notificationTurnOn: Int?
        get() = sharedPreference.getInt("notificationTurnOn", 0)
        set(value) = sharedPreference.edit {
            it.putInt("notificationTurnOn", value!!)
        }
    var updateDB: Int?
        get() = sharedPreference.getInt("updateDB", 0)
        set(value) = sharedPreference.edit {
            it.putInt("updateDB", value!!)
        }
    var updateDBTime: String?
        get() = sharedPreference.getString("updateDBTime", "")
        set(value) = sharedPreference.edit {
            it.putString("updateDBTime", value)
        }
    var lang: String?
        get() = sharedPreference.getString("lang", "")
        set(value) = sharedPreference.edit {
            it.putString("lang", value!!)
        }
    var selectLanguage: Int?
        get() = sharedPreference.getInt("selectLanguage", 1)
        set(value) = sharedPreference.edit {
            it.putInt("selectLanguage", value!!)
        }

}