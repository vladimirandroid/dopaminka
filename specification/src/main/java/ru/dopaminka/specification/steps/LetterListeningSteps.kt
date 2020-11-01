package ru.dopaminka.specification.steps

import io.cucumber.java8.En
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dopaminka.entity.Alphabet
import ru.dopaminka.specification.State
import ru.dopaminka.usecases.alphabet.GetAlphabet
import ru.dopaminka.usecases.task.CreateLetterListeningTask
import ru.dopaminka.usecases.task.GetTask

@KoinApiExtension
class LetterListeningSteps : En, KoinComponent {

    private val state: State by inject()
    private val getAlphabet: GetAlphabet by inject()
    private val createLetterListeningTask: CreateLetterListeningTask by inject()
    private val getTask: GetTask by inject()

    init {
        When("админ создает задание ПрослушиваниеБуквы") {
            state.alphabetView = getAlphabet.execute(Alphabet.idFromLanguage(Alphabet.Language.ru))
            val letter = state.alphabetView!!.letters[0]
            state.taskId =
                createLetterListeningTask.execute(CreateLetterListeningTask.Params(letter))
        }
        Then("появляется задание ПрослушиваниеБуквы") {
            val task = getTask.execute(state.taskId!!)
            assertNotNull(task)
            assertTrue(task is GetTask.LetterListeningTaskView)
        }
    }
}