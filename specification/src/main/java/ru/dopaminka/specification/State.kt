package ru.dopaminka.specification

import ru.dopaminka.entity.Alphabet
import ru.dopaminka.entity.Lesson
import ru.dopaminka.entity.LessonProgress
import ru.dopaminka.entity.Program
import ru.dopaminka.entity.common.Identity
import ru.dopaminka.entity.tasks.Task
import ru.dopaminka.persistence.InMemoryRepositoryImpl
import ru.dopaminka.usecases.Repository
import ru.dopaminka.usecases.alphabet.GetAlphabet
import ru.dopaminka.usecases.lesson.GetLesson
import ru.dopaminka.usecases.lesson.GetUnassignedLessons
import ru.dopaminka.usecases.task.GetUnassignedTasks

class State {
    companion object {
        fun clear() {
            alphabetRepository.clear()
            lessonProgressRepository.clear()
            programRepository.clear()
            lessonRepository.clear()
            taskRepository.clear()
            alphabetId = null
            lessonId = null
            taskId = null
            lessonView = null
            alphabetView = null
        }

        val alphabetRepository: Repository<Alphabet> = InMemoryRepositoryImpl()
        val lessonProgressRepository: Repository<LessonProgress> = InMemoryRepositoryImpl()
        val programRepository: Repository<Program> = InMemoryRepositoryImpl()
        val lessonRepository: Repository<Lesson> = InMemoryRepositoryImpl()
        val taskRepository: Repository<Task> = InMemoryRepositoryImpl()

        var unassignedLessons: List<GetUnassignedLessons.LessonView>? = null
        var unassignedTasks: List<GetUnassignedTasks.TaskView>? = null

        var alphabetId: Identity? = null
        var lessonId: Identity? = null
        var taskId: Identity? = null

        var lessonView: GetLesson.LessonView? = null
        var alphabetView: GetAlphabet.AlphabetView? = null
    }
}