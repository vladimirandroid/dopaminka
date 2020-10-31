package ru.dopaminka.specification.steps

import io.cucumber.java8.En
import io.cucumber.java8.HookNoArgsBody
import org.junit.Assert.*
import ru.dopaminka.entity.Alphabet
import ru.dopaminka.specification.State
import ru.dopaminka.usecases.alphabet.*

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
                    "sound_a.mp3"
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
                    "sound_a.mp3"
                )
            )
        }
        When("админ удаляет букву из алфавита") {
            RemoveLetter(State.alphabetRepository).execute(
                RemoveLetter.Params(
                    State.alphabetId!!,
                    "А",
                    "sound_a.mp3"
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
                        "sound_a.mp3"
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
        When("админ меняет озвучку букве") {
            UpdateSound(State.alphabetRepository).execute(
                UpdateSound.Params(
                    State.alphabetId!!,
                    "А",
                    "b_sound.mp3"
                )
            )
        }
        And("в алфавите осталась одна буква") {
            State.alphabetView = GetAlphabet(State.alphabetRepository).execute(State.alphabetId!!)
            assertEquals(1, State.alphabetView!!.letters.size)
        }
        Then("озвучка буквы заменяется на новую") {
            State.alphabetView = GetAlphabet(State.alphabetRepository).execute(State.alphabetId!!)
            val letter = State.alphabetView!!.letters[0]
            assertEquals("А", letter.text)
            assertEquals("b_sound.mp3", letter.soundFileName)
        }
        And("у буквы есть произношение") {
            State.alphabetView = GetAlphabet(State.alphabetRepository).execute(State.alphabetId!!)
            val letter = State.alphabetView!!.letters[0]
            assertTrue(letter.soundFileName.isNotEmpty())
        }
    }
}