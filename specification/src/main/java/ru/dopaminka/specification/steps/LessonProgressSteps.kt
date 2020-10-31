package ru.dopaminka.specification.steps

import io.cucumber.java8.En
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import ru.dopaminka.specification.State
import ru.dopaminka.usecases.lesson.GetLesson
import ru.dopaminka.usecases.lessonProgress.CompleteTask

class LessonProgressSteps : En {
    init {
        When("ученик завершает задание") {
            CompleteTask(
                State.lessonRepository,
                State.taskRepository,
                State.lessonProgressRepository
            ).execute(CompleteTask.Params(State.lessonId!!, State.taskId!!))
        }
        Then("задание помечается как завершенное") {
            State.lessonView =
                GetLesson(
                    State.lessonRepository,
                    State.taskRepository,
                    State.lessonProgressRepository
                ).execute(State.lessonId!!)
            assertTrue(State.lessonView!!.tasks[0].isCompleted)
        }
        And("прогресс урока становится = {double}") { progress: Double ->
            assertEquals(progress, State.lessonView!!.progress, 0.0001)
        }
    }
}