package ru.dopaminka.specification.steps

import io.cucumber.java8.En
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dopaminka.entity.tasks.ListenAndSelectTask
import ru.dopaminka.specification.State
import ru.dopaminka.usecases.task.AnswerToListenAndSelectTask
import ru.dopaminka.usecases.task.CreateListenAndSelectTask
import ru.dopaminka.usecases.task.GetTask

@KoinApiExtension
class ListenAndSelectTaskSteps : En, KoinComponent {

    private val state: State by inject()
    private val createListenAndSelectTask: CreateListenAndSelectTask by inject()
    private val getTask: GetTask by inject()
    private val answerToListenAndSelectTask: AnswerToListenAndSelectTask by inject()

    init {
        When("админ создает задание ПрослушатьИВыбрать") {
            state.taskId =
                createListenAndSelectTask.execute(
                    CreateListenAndSelectTask.Params(
                        "char_01.png",
                        "01.mp3",
                        "char_02.png"
                    )
                )
        }
        When("есть задание ПрослушатьИВыбрать") {
            state.taskId =
                createListenAndSelectTask.execute(
                    CreateListenAndSelectTask.Params(
                        "char_01.png",
                        "01.mp3",
                        "char_02.png"
                    )
                )
        }
        Then("появляется задание ПрослушатьИВыбрать") {
            val task = getTask.execute(state.taskId!!)
            assertNotNull(task)
            assertTrue(task is GetTask.ListenAndSelectTaskView)
        }
        When("ученик отвечает правильно на задание ПрослушатьИВыбрать") {
            val task = getTask.execute(state.taskId!!)
            val t = task as GetTask.ListenAndSelectTaskView
            answerToListenAndSelectTask.execute(
                AnswerToListenAndSelectTask.Params(
                    state.lessonId!!,
                    state.taskId!!,
                    t.image
                )
            )
        }
        When("ученик отвечает неправильно на задание ПрослушатьИВыбрать") {
            val task = getTask.execute(state.taskId!!)
            val t = task as GetTask.ListenAndSelectTaskView
            answerToListenAndSelectTask.execute(
                AnswerToListenAndSelectTask.Params(
                    state.lessonId!!,
                    state.taskId!!,
                    t.wrongImage
                )
            )
        }
    }
}