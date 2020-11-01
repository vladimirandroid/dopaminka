package ru.dopaminka.specification

import ru.dopaminka.entity.Alphabet
import ru.dopaminka.entity.Lesson
import ru.dopaminka.entity.LessonProgress
import ru.dopaminka.entity.Program
import ru.dopaminka.entity.common.Identity
import ru.dopaminka.entity.tasks.Task
import ru.dopaminka.usecases.Repository
import ru.dopaminka.usecases.alphabet.GetAlphabet
import ru.dopaminka.usecases.lesson.GetLesson
import ru.dopaminka.usecases.lesson.GetUnassignedLessons
import ru.dopaminka.usecases.task.GetUnassignedTasks

class State(
    val alphabetRepository: Repository<Alphabet>,
    val lessonProgressRepository: Repository<LessonProgress>,
    val programRepository: Repository<Program>,
    val lessonRepository: Repository<Lesson>,
    val taskRepository: Repository<Task>
) {
//    fun clear() {
//        alphabetRepository.clear()
//        lessonProgressRepository.clear()
//        programRepository.clear()
//        lessonRepository.clear()
//        taskRepository.clear()
//        alphabetId = null
//        lessonId = null
//        taskId = null
//        lessonView = null
//        alphabetView = null
//    }

    var unassignedLessons: List<GetUnassignedLessons.LessonView>? = null
    var unassignedTasks: List<GetUnassignedTasks.TaskView>? = null

    var alphabetId: Identity? = null
    var lessonId: Identity? = null
    var taskId: Identity? = null

    var lessonView: GetLesson.LessonView? = null
    var alphabetView: GetAlphabet.AlphabetView? = null
}