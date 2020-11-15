package ru.dopaminka.specification.steps

import io.cucumber.java8.En
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dopaminka.specification.State
import ru.dopaminka.usecases.program.CreateProgram
import ru.dopaminka.usecases.program.GetLessons
import ru.dopaminka.usecases.program.SetLessons

@KoinApiExtension
class ProgramSteps : En, KoinComponent {

    private val state: State by inject()
    private val createProgram: CreateProgram by inject()
    private val setLessons: SetLessons by inject()
    private val getLessons: GetLessons by inject()

    lateinit var lessons: List<GetLessons.LessonView>

    init {
        Given("есть программа обучения") {
            createProgram.execute(Unit)
        }
        And("урок входит в программу") {
            setLessons.execute(
                SetLessons.Params(
                    listOf(state.lessonId!!)
                )
            )
        }
        When("ученик запрашивает список уроков") {
            lessons = getLessons.execute(Unit)
        }
        Then("ученик видит список уроков") {
            assertNotNull(lessons)
            assertTrue(lessons.isNotEmpty())
        }
        And("для каждого урока ученик видит: идентификатор, заголовок") {
            lessons.forEach {
                assertTrue(it.title.isNotEmpty())
                assertNotNull(it.id)
            }
        }
    }
}