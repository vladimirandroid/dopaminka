package ru.dopaminka.specification.steps.program

import io.cucumber.java8.En
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dopaminka.entity.program.Lesson
import ru.dopaminka.entity.program.Program
import ru.dopaminka.entity.reading.Letter
import ru.dopaminka.entity.readingProgram.ListenAndSelectTextTask
import ru.dopaminka.entity.readingProgram.ListenTask
import ru.dopaminka.specification.TestProgramProvider
import ru.dopaminka.usecases.ProgramProvider

@KoinApiExtension
class ProgramSteps : En, KoinComponent {

    private val programProvider: ProgramProvider by inject()

    lateinit var lessons: List<Lesson>

    init {
        Given("есть программа") {
            val program = Program(mutableListOf())

            val programProvider = programProvider as TestProgramProvider
            programProvider.program = program
        }
        Given("в программе {int} уроков") { count: Int ->
            val lessons = programProvider.get().lessons as MutableList
            for (i in 1..count) lessons.add(Lesson(mutableListOf()))
        }
        Given("в {int} уроке {int} заданий") { lessonNumber: Int, tasksCount: Int ->
            val lesson = programProvider.get().lessons[lessonNumber]
            val tasks = lesson.tasks as MutableList

            for (i in 1..tasksCount) tasks.add(ListenTask(Letter("", "", emptyList())))
        }
        Given("в {int} уроке {int} заданий типа {string}") { lessonNumber: Int, tasksCount: Int, taskType: String ->
            val lesson = programProvider.get().lessons[lessonNumber]
            val tasks = lesson.tasks as MutableList
            for (i in 1..tasksCount) {
                if (taskType == "_прослушать_и_выбрать_текст_") {
                    tasks.add(
                        ListenAndSelectTextTask(
                            Letter("", "", emptyList()),
                            Letter("", "", emptyList())
                        )
                    )
                } else if (taskType == "_прослушать_") {
                    tasks.add(
                        ListenTask(
                            Letter("", "", emptyList())
                        )
                    )
                }
            }
        }
        When("ученик смотрит программу") {
            lessons = programProvider.get().lessons
        }
        Then("ученик видит список уроков") {
            assertNotNull(lessons)
            assertTrue(lessons.isNotEmpty())
        }
        Then("в каждом уроке есть задания") {
            assertTrue(lessons.all { it.tasks.isNotEmpty() })
        }
    }
}