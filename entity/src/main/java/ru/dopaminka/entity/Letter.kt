package ru.dopaminka.entity

import sun.misc.Resource
import kotlin.properties.Delegates

class Letter {
    var sound by Delegates.notNull<Int>()
    lateinit var text: String
    fun setSound() {

    }
}