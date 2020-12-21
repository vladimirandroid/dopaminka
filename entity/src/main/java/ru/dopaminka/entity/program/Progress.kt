package ru.dopaminka.entity.program

class Progress(
    private var _currentLesson: Lesson,
    private var _currentTask: Task,
    private var _isProgramCompleted: Boolean,
    private val program: Program
) {

    val isProgramCompleted
        get() = _isProgramCompleted
    val currentLesson: Lesson
        get() = _currentLesson
    val currentTask: Task
        get() = _currentTask

    fun complete(lesson: Lesson, task: Task) {
        if (lesson != currentLesson) return

        if (isLastTask(task)) {
            if (isCurrentLessonLast()) {
                _isProgramCompleted = true
            } else {
                _currentLesson = getNextLesson()
                _currentTask = _currentLesson.tasks.first()
            }
        } else if (task == _currentTask) {
            _currentTask = getNextTask()
        }
    }

    private fun isLastTask(task: Task) = _currentLesson.tasks.last() == task

    private fun isCurrentLessonLast() = program.lessons.last() == _currentLesson

    private fun getNextTask(): Task {
        val index = _currentLesson.tasks.indexOf(_currentTask)
        return _currentLesson.tasks[index + 1]
    }

    private fun getNextLesson(): Lesson {
        val index = program.lessons.indexOf(_currentLesson)
        return program.lessons[index + 1]
    }
}