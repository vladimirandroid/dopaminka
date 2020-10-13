package ru.dopaminka.entity.common

import java.util.*

data class Identity(val id: String) {
    companion object {
        fun generate() = Identity(UUID.randomUUID().toString())
    }
}