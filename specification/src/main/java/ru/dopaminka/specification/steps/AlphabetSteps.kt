package ru.dopaminka.specification.steps

import io.cucumber.java8.En
import io.cucumber.java8.HookNoArgsBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import ru.dopaminka.entity.Alphabet
import ru.dopaminka.specification.State
import ru.dopaminka.usecases.alphabet.AddLetter
import ru.dopaminka.usecases.alphabet.CreateAlphabet
import ru.dopaminka.usecases.alphabet.GetAlphabet
import ru.dopaminka.usecases.alphabet.RemoveLetter

class AlphabetSteps : En {

    var exception: Exception? = null


    init {
        Before(HookNoArgsBody { State.clear() })

        When("админ создаёт алфавит для русского языка") {
            State.alphabetId =
                CreateAlphabet(State.alphabetRepository).execute(Alphabet.Language.ru)
        }
        Then("алфавит появляется") {
            State.alphabetView = GetAlphabet(State.alphabetRepository).execute(State.alphabetId!!)
        }
        And("в алфавите нет букв") {
            assertEquals(0, State.alphabetView!!.letters.size)
        }
        And("язык алфавита - русский") {
            assertEquals(Alphabet.Language.ru, State.alphabetView!!.language)
        }
        Given("есть алфавит") {
            State.alphabetId =
                CreateAlphabet(State.alphabetRepository).execute(Alphabet.Language.ru)
        }
        When("админ добавляет букву в алфавит") {
            AddLetter(State.alphabetRepository).execute(
                AddLetter.Params(
                    State.alphabetId!!,
                    "А",
                )
            )
        }
        Then("буква появляется в алфавите") {
            State.alphabetView = GetAlphabet(State.alphabetRepository).execute(State.alphabetId!!)
            assertEquals(1, State.alphabetView!!.letters.size)
        }
        And("есть буква в алфавите") {
            AddLetter(State.alphabetRepository).execute(
                AddLetter.Params(
                    State.alphabetId!!,
                    "А",
                )
            )
        }
        When("админ удаляет букву из алфавита") {
            RemoveLetter(State.alphabetRepository).execute(
                RemoveLetter.Params(
                    State.alphabetId!!,
                    "А",
                )
            )
        }
        Then("буква исчезает из алфавита") {
            State.alphabetView = GetAlphabet(State.alphabetRepository).execute(State.alphabetId!!)
            assertEquals(0, State.alphabetView!!.letters.size)
        }
        When("админ добавляет дубликат буквы в алфавит") {
            try {
                AddLetter(State.alphabetRepository).execute(
                    AddLetter.Params(
                        State.alphabetId!!,
                        "А",
                    )
                )
            } catch (e: Exception) {
                exception = e
            }
        }
        Then("дубликат не появился") {
            State.alphabetView = GetAlphabet(State.alphabetRepository).execute(State.alphabetId!!)
            assertEquals(1, State.alphabetView!!.letters.size)
        }
        And("произошла ошибка") {
            assertNotNull(exception)
        }
        And("в алфавите осталась одна буква") {
            State.alphabetView = GetAlphabet(State.alphabetRepository).execute(State.alphabetId!!)
            assertEquals(1, State.alphabetView!!.letters.size)
        }
    }
}