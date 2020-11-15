package ru.dopaminka.specification

import ru.dopaminka.entity.Lesson
import ru.dopaminka.entity.LessonProgress
import ru.dopaminka.entity.Program
import ru.dopaminka.entity.common.Identity
import ru.dopaminka.entity.tasks.Task
import ru.dopaminka.usecases.Repository
import ru.dopaminka.usecases.lesson.GetLessonTasks
import ru.dopaminka.usecases.lesson.GetUnassignedLessons
import ru.dopaminka.usecases.task.GetUnassignedTasks

class State(
    val lessonProgressRepository: Repository<LessonProgress>,
    val programRepository: Repository<Program>,
    val lessonRepository: Repository<Lesson>,
    val taskRepository: Repository<Task>
) {
    var unassignedLessons: List<GetUnassignedLessons.LessonView>? = null
    var unassignedTasks: List<GetUnassignedTasks.TaskView>? = null

    var lessonId: Identity? = null
    var taskId: Identity? = null

    var lessonTasksView: GetLessonTasks.LessonView? = null
}