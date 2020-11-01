package ru.dopaminka.specification.steps

import io.cucumber.java8.En
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dopaminka.entity.Alphabet
import ru.dopaminka.specification.State
import ru.dopaminka.usecases.program.CreateProgram
import ru.dopaminka.usecases.program.GetProgram
import ru.dopaminka.usecases.program.SetProgramLessons

@KoinApiExtension
class ProgramSteps : En, KoinComponent {

    private val state: State by inject()
    private val createProgram: CreateProgram by inject()
    private val setProgramLessons: SetProgramLessons by inject()
    private val getProgram: GetProgram by inject()

    lateinit var programView: GetProgram.ProgramView

    init {
        Given("есть программа для русского языка") {
            createProgram.execute(Alphabet.Language.ru)
        }
        And("урок входит в программу") {
            setProgramLessons.execute(
                SetProgramLessons.Params(
                    Alphabet.Language.ru,
                    listOf(state.lessonId!!)
                )
            )
        }
        When("ученик запрашивает программу для русского языка") {
            programView = getProgram.execute(GetProgram.Params(Alphabet.Language.ru))
        }
        Then("ученик видит программу: язык и список уроков") {
            assertNotNull(programView)
            assertTrue(programView.language.isNotEmpty())
            assertTrue(programView.lessons.isNotEmpty())
        }
        And("для каждого урока ученик видит: идентификатор, заголовок") {
            programView.lessons.forEach {
                assertTrue(it.title.isNotEmpty())
                assertNotNull(it.id)
            }
        }
    }
}