package ru.dopaminka.entity

class Lesson{
    private var tasks: MutableList<Task> = mutableListOf()

    fun addTask(task: Task){
        tasks.add(task)
    }

    fun removeTask(task: Task){
        tasks.remove(task);
    }
}