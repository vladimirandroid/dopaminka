package ru.dopaminka.entity

import ru.dopaminka.entity.common.Entity
import ru.dopaminka.entity.common.Identity

class Program(id: Identity, val language: Alphabet.Language) : Entity(id) {
    private val _lessons = mutableListOf<Identity>()
    val lessons: List<Identity>
        get() = _lessons

    fun setLessons(lessonsIdentities: List<Identity>) {
        _lessons.clear()
        _lessons.addAll(lessonsIdentities)
    }
}