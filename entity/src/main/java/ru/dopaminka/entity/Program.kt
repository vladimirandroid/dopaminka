package ru.dopaminka.entity

import ru.dopaminka.entity.common.Entity
import ru.dopaminka.entity.common.Identity

class Program(id: Identity) : Entity(id) {
    private val _lessonsIds = mutableListOf<Identity>()
    val lessonsIds: List<Identity>
        get() = _lessonsIds

    fun setLessons(lessonsIdentities: List<Identity>) {
        _lessonsIds.clear()
        _lessonsIds.addAll(lessonsIdentities)
    }
}