package ru.dopaminka.specification.steps

import io.cucumber.java8.En
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import ru.dopaminka.entity.Alphabet
import ru.dopaminka.specification.State
import ru.dopaminka.usecases.alphabet.AddLetter
import ru.dopaminka.usecases.alphabet.CreateAlphabet
import ru.dopaminka.usecases.alphabet.GetAlphabet
import ru.dopaminka.usecases.alphabet.RemoveLetter

@KoinApiExtension
class AlphabetSteps : En, KoinComponent {

    private val state: State by inject()
    private val addLetter: AddLetter by inject()
    private val createAlphabet: CreateAlphabet by inject()
    private val getAlphabet: GetAlphabet by inject()
    private val removeLetter: RemoveLetter by inject()

    private var exception: Exception? = null

    init {
        When("админ создаёт алфавит для русского языка") {
            state.alphabetId = get<CreateAlphabet>().execute(Alphabet.Language.ru)
        }
        Then("алфавит появляется") {
            state.alphabetView = GetAlphabet(state.alphabetRepository).execute(state.alphabetId!!)
        }
        And("в алфавите нет букв") {
            assertEquals(0, state.alphabetView!!.letters.size)
        }
        And("язык алфавита - русский") {
            assertEquals(Alphabet.Language.ru, state.alphabetView!!.language)
        }
        Given("есть алфавит") {
            state.alphabetId = createAlphabet.execute(Alphabet.Language.ru)
        }
        When("админ добавляет букву в алфавит") {
            addLetter.execute(AddLetter.Params(state.alphabetId!!, "А"))
        }
        Then("буква появляется в алфавите") {
            state.alphabetView = getAlphabet.execute(state.alphabetId!!)
            assertEquals(1, state.alphabetView!!.letters.size)
        }
        And("есть буква в алфавите") {
            addLetter.execute(AddLetter.Params(state.alphabetId!!, "А"))
        }
        When("админ удаляет букву из алфавита") {
            removeLetter.execute(RemoveLetter.Params(state.alphabetId!!, "А"))
        }
        Then("буква исчезает из алфавита") {
            state.alphabetView = getAlphabet.execute(state.alphabetId!!)
            assertEquals(0, state.alphabetView!!.letters.size)
        }
        When("админ добавляет дубликат буквы в алфавит") {
            try {
                addLetter.execute(AddLetter.Params(state.alphabetId!!, "А"))
            } catch (e: Exception) {
                exception = e
            }
        }
        Then("дубликат не появился") {
            state.alphabetView = getAlphabet.execute(state.alphabetId!!)
            assertEquals(1, state.alphabetView!!.letters.size)
        }
        And("произошла ошибка") {
            assertNotNull(exception)
        }
        And("в алфавите осталась одна буква") {
            state.alphabetView = getAlphabet.execute(state.alphabetId!!)
            assertEquals(1, state.alphabetView!!.letters.size)
        }
    }
}