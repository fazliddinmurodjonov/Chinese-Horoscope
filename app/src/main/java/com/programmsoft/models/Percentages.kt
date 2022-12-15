package com.programmsoft.models

class Percentages {
    var todayWork: Int? = null
    var todayHealth: Int? = null
    var todayLove: Int? = null
    var tomorrowWork: Int? = null
    var tomorrowHealth: Int? = null
    var tomorrowLove: Int? = null

    constructor()

    constructor(
        todayWork: Int?,
        todayHealth: Int?,
        todayLove: Int?,
        tomorrowWork: Int?,
        tomorrowHealth: Int?,
        tomorrowLove: Int?,
    ) {
        this.todayWork = todayWork
        this.todayHealth = todayHealth
        this.todayLove = todayLove
        this.tomorrowWork = tomorrowWork
        this.tomorrowHealth = tomorrowHealth
        this.tomorrowLove = tomorrowLove
    }

}