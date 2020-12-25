package ru.dopaminka.common

class ConsumableEvent<T>(val data: T) {

    private var isConsumed = false

    fun get(): T {
        return data
    }

    fun getIfNotConsumed(): T? {
        if (isConsumed) return null
        isConsumed = true
        return data
    }
}