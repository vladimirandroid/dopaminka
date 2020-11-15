package ru.dopaminka.specification.steps

import io.cucumber.java8.En
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dopaminka.specification.State
import ru.dopaminka.usecases.task.CompleteListenTask
import ru.dopaminka.usecases.task.CreateListenTask
import ru.dopaminka.usecases.task.GetTask

@KoinApiExtension
class ListenTaskSteps : En, KoinComponent {

    private val state: State by inject()
    private val createListenTask: CreateListenTask by inject()
    private val getTask: GetTask by inject()
    private val completeListenTask: CompleteListenTask by inject()

    init {
        When("ученик завершает задание Прослушать") {
            completeListenTask.execute(CompleteListenTask.Params(state.lessonId!!, state.taskId!!))
        }
        When("админ создает задание Прослушать") {
            state.taskId =
                createListenTask.execute(CreateListenTask.Params("char_01.png", "01.mp3"))
        }
        When("есть задание Прослушать") {
            state.taskId =
                createListenTask.execute(CreateListenTask.Params("char_01.png", "01.mp3"))
        }
        Then("появляется задание Прослушать") {
            val task = getTask.execute(state.taskId!!)
            assertNotNull(task)
            assertTrue(task is GetTask.ListenTaskView)
        }
    }
}