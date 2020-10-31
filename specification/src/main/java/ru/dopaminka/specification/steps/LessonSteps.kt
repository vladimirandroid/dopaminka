package ru.dopaminka.specification.steps

import io.cucumber.java8.En
import org.junit.Assert.*
import org.junit.function.ThrowingRunnable
import ru.dopaminka.specification.State
import ru.dopaminka.usecases.lesson.*

class LessonSteps : En {
    init {
        When("админ создает урок") {
            State.lessonId =
                CreateLesson(State.lessonRepository).execute(CreateLesson.Params("О, А, И"))
        }
        And("есть урок") {
            State.lessonId =
                CreateLesson(State.lessonRepository).execute(CreateLesson.Params("А, О, И"))
        }
        Then("урок появляется") {
            State.lessonView =
                GetLesson(
                    State.lessonRepository,
                    State.taskRepository,
                    State.lessonProgressRepository
                ).execute(State.lessonId!!)
            assertNotNull(State.lessonId)
            assertNotNull(State.lessonView)
        }
        And("в уроке нет заданий") {
            State.lessonView =
                GetLesson(
                    State.lessonRepository,
                    State.taskRepository,
                    State.lessonProgressRepository
                ).execute(State.lessonId!!)
            assertEquals(0, State.lessonView!!.tasks.size)
        }

        When("админ удаляет урок") {
            DeleteLesson(State.lessonRepository).execute(State.lessonId!!)
        }
        Then("урок удаляется") {
            assertThrows(Exception::class.java, ThrowingRunnable {
                State.lessonView =
                    GetLesson(
                        State.lessonRepository,
                        State.taskRepository, State.lessonProgressRepository
                    ).execute(State.lessonId!!)
            })
        }

        When("админ добавляет задание в урок") {
            AddTask(State.lessonRepository, State.taskRepository).execute(
                AddTask.Params(
                    State.lessonId!!,
                    State.taskId!!
                )
            )
        }
        Then("в уроке появляется задание") {
            State.lessonView =
                GetLesson(
                    State.lessonRepository,
                    State.taskRepository,
                    State.lessonProgressRepository
                ).execute(State.lessonId!!)
            assertEquals(1, State.lessonView!!.tasks.size)
        }
        And("задание добавлено в урок") {
            AddTask(State.lessonRepository, State.taskRepository).execute(
                AddTask.Params(
                    State.lessonId!!,
                    State.taskId!!
                )
            )
        }
        When("админ удаляет задание из урока") {
            RemoveTask(
                State.lessonRepository,
                State.taskRepository
            ).execute(RemoveTask.Params(State.lessonId!!, State.taskId!!))
        }
    }
}