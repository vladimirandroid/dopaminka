package ru.dopaminka.specification.steps

import io.cucumber.java8.En
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dopaminka.specification.State
import ru.dopaminka.usecases.task.CompleteListenTask
import ru.dopaminka.usecases.task.CreateListenTask
import ru.dopaminka.usecases.task.GetTask
import ru.dopaminka.usecases.task.GetUnassignedTasks

@KoinApiExtension
class TaskSteps : En, KoinComponent {

    private val state: State by inject()
    private val createListenTask: CreateListenTask by inject()
    private val getTask: GetTask by inject()
    private val getUnassignedTasks: GetUnassignedTasks by inject()
    private val completeListenTask: CompleteListenTask by inject()

    init {
        And("есть задание") {
            state.taskId =
                createListenTask.execute(CreateListenTask.Params("char_01.png", "01.mp3"))
        }
        When("админ создаёт задание") {
            state.taskId =
                createListenTask.execute(CreateListenTask.Params("char_01.png", "01.mp3"))
        }
        Then("задание появляется") {
            val task = getTask.execute(state.taskId!!)
            assertNotNull(task)
        }
        When("админ запрашивает непривязанные к урокам задания") {
            state.unassignedTasks = getUnassignedTasks.execute(Unit)
        }
        Then("он видит список с непривязанным к урокам заданием") {
            assertNotNull(state.unassignedTasks)
            assertEquals(1, state.unassignedTasks!!.size)
            assertEquals(state.taskId, state.unassignedTasks!![0].id)
        }
        When("ученик завершает задание") {
            completeListenTask.execute(CompleteListenTask.Params(state.lessonId!!, state.taskId!!))
        }
    }
}