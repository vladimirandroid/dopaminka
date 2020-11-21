package ru.dopaminka.entity.common

import java.io.Serializable
import java.util.*


data class Identity(val id: String) : Serializable {
    companion object {
        fun generate() = Identity(UUID.randomUUID().toString())
    }
}