package ru.dopaminka.specification.steps

import io.cucumber.java8.En
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import ru.dopaminka.entity.Alphabet
import ru.dopaminka.specification.State
import ru.dopaminka.usecases.alphabet.GetAlphabet
import ru.dopaminka.usecases.task.CreateLetterListeningTask
import ru.dopaminka.usecases.task.GetTask

class LetterListeningSteps : En {
    init {
        When("админ создает задание ПрослушиваниеБуквы") {
            State.alphabetView = GetAlphabet(State.alphabetRepository).execute(
                Alphabet.idFromLanguage(
                    Alphabet.Language.ru
                )
            )
            val letter = State.alphabetView!!.letters[0]
            State.taskId = CreateLetterListeningTask(State.taskRepository).execute(
                CreateLetterListeningTask.Params(letter)
            )
        }
        Then("появляется задание ПрослушиваниеБуквы") {
            val task = GetTask(State.taskRepository).execute(State.taskId!!)
            assertNotNull(task)
            assertTrue(task is GetTask.LetterListeningTaskView)
        }
    }
}