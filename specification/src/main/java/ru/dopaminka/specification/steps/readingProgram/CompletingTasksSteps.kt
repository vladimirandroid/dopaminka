package ru.dopaminka.specification.steps.readingProgram

import io.cucumber.java8.En
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dopaminka.entity.program.Lesson
import ru.dopaminka.entity.program.Program
import ru.dopaminka.entity.reading.Letter
import ru.dopaminka.entity.readingProgram.DrawTextTask
import ru.dopaminka.entity.readingProgram.ListenAndSelectTextTask
import ru.dopaminka.entity.readingProgram.ListenTask
import ru.dopaminka.specification.SpecificationProgramProvider
import ru.dopaminka.usecases.ProgramProvider
import ru.dopaminka.usecases.ProgressProvider
import ru.dopaminka.usecases.task.AnswerToListenAndSelectTextTask
import ru.dopaminka.usecases.task.CompleteDrawTextTask
import ru.dopaminka.usecases.task.CompleteListenTask

@KoinApiExtension
class CompletingTasksSteps : En, KoinComponent {

    private val programProvider: ProgramProvider by inject()
    private val progressProvider: ProgressProvider by inject()

    init {
        Given("есть программа с двумя уроками с одним заданием в каждом") {
            val task = ListenTask(Letter("", "", emptyList()))
            val lesson = Lesson(listOf(task))
            val program = Program(listOf(lesson))

            val programProvider = programProvider as SpecificationProgramProvider
            programProvider.program = program
        }
        When("ученик завершает {int} задание в {int} уроке") { taskNumber: Int, lessonNumber: Int ->

            val lesson = programProvider.get().lessons[lessonNumber]
            val task = lesson.tasks[taskNumber]
            progressProvider.get().complete(lesson, task)
        }
        Then("ученик переходит к {int} заданию в {int} уроке") { taskNumber: Int, lessonNumber: Int ->
            val lesson = programProvider.get().lessons[lessonNumber]
            val task = lesson.tasks[taskNumber]

            assertEquals(lesson, progressProvider.get().currentLesson)
            assertEquals(task, progressProvider.get().currentTask)
        }
        Then("ученик переходит к следующему заданию") {
            val lesson = programProvider.get().lessons.first()
            val task = lesson.tasks.first { it is ListenTask }
            progressProvider.get().complete(lesson, task)
        }
        Then("программа завершается") {
            assertTrue(progressProvider.get().isProgramCompleted)
        }


        When("ученик завершает {int} задание в {int} уроке типа {string}") { taskNumber: Int, lessonNumber: Int, taskType: String ->
            val lesson = programProvider.get().lessons[lessonNumber]
            when (taskType) {
                "_прослушать_и_выбрать_текст_" -> {
                    val task = lesson.tasks[taskNumber] as ListenAndSelectTextTask
                    val completer: AnswerToListenAndSelectTextTask by inject()
                    completer.execute(
                        AnswerToListenAndSelectTextTask.Params(
                            lesson,
                            task,
                            task.rightText
                        )
                    )
                }
                "_прослушать_" -> {
                    val task = lesson.tasks[taskNumber] as ListenTask
                    val completer: CompleteListenTask by inject()
                    completer.execute(
                        CompleteListenTask.Params(
                            lesson,
                            task,
                        )
                    )
                }
                "_обвести_текст_" -> {
                    val task = lesson.tasks[taskNumber] as DrawTextTask
                    val completer: CompleteDrawTextTask by inject()
                    completer.execute(
                        CompleteDrawTextTask.Params(
                            lesson,
                            task,
                        )
                    )
                }
            }
        }
    }
}