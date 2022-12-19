package com.programmsoft.models

class Zodiac {
    var id: Int? = null
    var name: String? = null
    var description: String? = null
    var img: String? = null


    constructor(id: Int?, name: String?, description: String?, img: String?) {
        this.id = id
        this.name = name
        this.description = description
        this.img = img
    }

    constructor()
}