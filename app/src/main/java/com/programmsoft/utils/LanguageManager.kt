package com.programmsoft.utils

import android.content.Context
import android.content.res.Configuration
import java.util.*

class LanguageManager {
    var context: Context? = null

    constructor(context: Context?) {
        this.context = context
    }

    fun updateResource(code: String) {
        val locale = Locale(code)
        Locale.setDefault(locale)
        val resources = context!!.resources
        val configuration: Configuration = resources.configuration
        configuration.locale = locale
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}