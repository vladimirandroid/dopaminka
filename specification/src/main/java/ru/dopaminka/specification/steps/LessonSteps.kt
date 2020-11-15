package ru.dopaminka.specification.steps

import io.cucumber.java8.En
import org.junit.Assert.*
import org.junit.function.ThrowingRunnable
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dopaminka.specification.State
import ru.dopaminka.usecases.lesson.*
import ru.dopaminka.usecases.task.CompleteListenTask

@KoinApiExtension
class LessonSteps : En, KoinComponent {

    private val state: State by inject()
    private val createLesson: CreateLesson by inject()
    private val getLessonTasks: GetLessonTasks by inject()
    private val deleteLesson: DeleteLesson by inject()
    private val addTask: AddTask by inject()
    private val removeTask: RemoveTask by inject()
    private val getUnassignedLessons: GetUnassignedLessons by inject()

    init {
        When("админ создает урок") {
            state.lessonId = createLesson.execute(CreateLesson.Params("О, А, И"))
        }
        And("есть урок") {
            state.lessonId = createLesson.execute(CreateLesson.Params("А, О, И"))
        }
        Then("урок появляется") {
            state.lessonTasksView = getLessonTasks.execute(state.lessonId!!)
            assertNotNull(state.lessonId)
            assertNotNull(state.lessonTasksView)
        }
        And("в уроке нет заданий") {
            state.lessonTasksView = getLessonTasks.execute(state.lessonId!!)
            assertEquals(0, state.lessonTasksView!!.tasks.size)
        }

        When("админ удаляет урок") {
            deleteLesson.execute(state.lessonId!!)
        }
        Then("урок удаляется") {
            assertThrows(Exception::class.java, ThrowingRunnable {
                state.lessonTasksView = getLessonTasks.execute(state.lessonId!!)
            })
        }

        When("админ добавляет задание в урок") {
            addTask.execute(AddTask.Params(state.lessonId!!, state.taskId!!))
        }
        Then("в уроке появляется задание") {
            state.lessonTasksView = getLessonTasks.execute(state.lessonId!!)
            assertEquals(1, state.lessonTasksView!!.tasks.size)
        }
        And("задание добавлено в урок") {
            addTask.execute(AddTask.Params(state.lessonId!!, state.taskId!!))
        }
        When("админ удаляет задание из урока") {
            removeTask.execute(RemoveTask.Params(state.lessonId!!, state.taskId!!))
        }
        When("админ запрашивает непривязанные к программам уроки") {
            state.unassignedLessons = getUnassignedLessons.execute(Unit)
        }
        Then("он видит список с непривязанным к программам уроком") {
            assertNotNull(state.unassignedLessons)
            assertEquals(1, state.unassignedLessons!!.size)
            assertEquals(state.lessonId, state.unassignedLessons!![0].id)
        }
        Then("задание помечается как завершенное") {
            state.lessonTasksView = getLessonTasks.execute(state.lessonId!!)
            assertTrue(state.lessonTasksView!!.tasks[0].isCompleted)
        }
        Then("задание не помечается как завершенное") {
            state.lessonTasksView = getLessonTasks.execute(state.lessonId!!)
            assertFalse(state.lessonTasksView!!.tasks[0].isCompleted)
        }
        And("прогресс урока становится = {double}") { progress: Double ->
            assertEquals(progress, state.lessonTasksView!!.progress, 0.0001)
        }
    }
}