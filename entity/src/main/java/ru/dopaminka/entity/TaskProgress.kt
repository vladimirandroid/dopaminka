package ru.dopaminka.entity

import ru.dopaminka.entity.common.Identity

class TaskProgress(val taskId: Identity, var completed: Boolean) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TaskProgress

        if (taskId != other.taskId) return false

        return true
    }

    override fun hashCode(): Int {
        return taskId.hashCode()
    }
}