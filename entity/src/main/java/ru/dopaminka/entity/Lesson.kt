package ru.dopaminka.entity

import ru.dopaminka.entity.common.Entity
import ru.dopaminka.entity.common.Identity
import ru.dopaminka.entity.tasks.Task

class Lesson(id: Identity, val title: String) : Entity(id) {

    private val _tasksIds = mutableListOf<Identity>()

    val tasksIds: List<Identity>
        get() = _tasksIds

    fun addTask(task: Task) {
        _tasksIds.add(task.id)
    }

    fun removeTask(task: Task) {
        _tasksIds.remove(task.id)
    }
}