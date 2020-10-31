package ru.dopaminka.entity

import ru.dopaminka.entity.common.Entity
import ru.dopaminka.entity.common.Identity
import ru.dopaminka.entity.tasks.Task

class LessonProgress(id: Identity) : Entity(id) {

    private val completedTaskIds = mutableListOf<Identity>()
    val completedTasksCount
        get() = completedTaskIds.size

    fun completeTask(task: Task) {
        completedTaskIds.add(task.id)
    }

    fun isTaskCompleted(task: Task) = completedTaskIds.contains(task.id)
}