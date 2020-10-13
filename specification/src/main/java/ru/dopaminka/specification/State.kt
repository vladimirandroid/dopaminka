package ru.dopaminka.specification

val _state = mutableMapOf<Any, Any>()

val STATE: MutableMap<Any, Any>
    get() {
        println("get STATE")
        return _state
    }