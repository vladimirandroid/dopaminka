package ru.dopaminka.entity

import ru.dopaminka.entity.common.Identity

abstract class Task(val id: Identity) {
    private var _illustrations = mutableListOf<Illustration>()

    var completed = false
    val illustrations: List<Illustration>
        get() = _illustrations


    fun complete(): TaskProgress = TaskProgress(id, true)
    fun addIllustration(illustration: Illustration) {}
    fun removeIllustrtion(illustration: Illustration) {}
}

