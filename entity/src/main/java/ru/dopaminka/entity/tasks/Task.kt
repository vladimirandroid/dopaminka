package ru.dopaminka.entity.tasks

import ru.dopaminka.entity.common.Entity
import ru.dopaminka.entity.common.Identity

abstract class Task(id: Identity) : Entity(id) {
    private var _illustrations = mutableListOf<Illustration>()

    val illustrations: List<Illustration>
        get() = _illustrations

    fun addIllustration(illustration: Illustration) {
        _illustrations.add(illustration)
    }

    fun removeIllustration(illustration: Illustration) {
        _illustrations.remove(illustration)
    }

    data class Illustration(val imageFileName: String, val soundFileName: String)
}

