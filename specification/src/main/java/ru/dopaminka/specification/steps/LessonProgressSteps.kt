package ru.dopaminka.specification.steps

import io.cucumber.java8.En
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dopaminka.specification.State
import ru.dopaminka.usecases.lesson.GetLesson
import ru.dopaminka.usecases.lessonProgress.CompleteTask

@KoinApiExtension
class LessonProgressSteps : En, KoinComponent {

    private val completeTask: CompleteTask by inject()
    private val getLesson: GetLesson by inject()
    private val state: State by inject()

    init {
        When("ученик завершает задание") {
            completeTask.execute(CompleteTask.Params(state.lessonId!!, state.taskId!!))
        }
        Then("задание помечается как завершенное") {
            state.lessonView = getLesson.execute(state.lessonId!!)
            assertTrue(state.lessonView!!.tasks[0].isCompleted)
        }
        And("прогресс урока становится = {double}") { progress: Double ->
            assertEquals(progress, state.lessonView!!.progress, 0.0001)
        }
    }
}