package ru.dopaminka.specification.steps

import io.cucumber.java8.En
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import ru.dopaminka.entity.Alphabet
import ru.dopaminka.specification.State
import ru.dopaminka.usecases.program.CreateProgram
import ru.dopaminka.usecases.program.GetProgram
import ru.dopaminka.usecases.program.SetProgramLessons

class ProgramSteps : En {
    lateinit var programView: GetProgram.ProgramView

    init {
        Given("есть программа для русского языка") {
            CreateProgram(State.programRepository).execute(Alphabet.Language.ru)
        }
        And("урок входит в программу") {
            SetProgramLessons(State.programRepository, State.lessonRepository).execute(
                SetProgramLessons.Params(Alphabet.Language.ru, listOf(State.lessonId!!))
            )
        }
        When("ученик запрашивает программу для русского языка") {
            programView = GetProgram(
                State.programRepository,
                State.lessonRepository
            ).execute(GetProgram.Params(Alphabet.Language.ru))
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