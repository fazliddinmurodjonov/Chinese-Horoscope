package com.programmsoft.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ZodiacBase {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var name: String? = null
    var todayUzb: String? = null
    var tomorrowUzb: String? = null
    var dateToday: String? = null
    var dateTomorrow: String? = null
    var workToday: Int? = null
    var healthToday: Int? = null
    var loveToday: Int? = null
    var workTomorrow: Int? = null
    var healthTomorrow: Int? = null
    var loveTomorrow: Int? = null
}