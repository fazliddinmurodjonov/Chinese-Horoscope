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
}