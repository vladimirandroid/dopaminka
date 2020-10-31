package ru.dopaminka.specification.steps

import io.cucumber.java8.En
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import ru.dopaminka.entity.Alphabet
import ru.dopaminka.specification.State
import ru.dopaminka.usecases.alphabet.AddLetter
import ru.dopaminka.usecases.alphabet.CreateAlphabet
import ru.dopaminka.usecases.alphabet.GetAlphabet
import ru.dopaminka.usecases.task.*

class TaskSteps : En {
    init {
        And("есть задание") {
            State.alphabetId =
                CreateAlphabet(State.alphabetRepository).execute(Alphabet.Language.ru)
            AddLetter(State.alphabetRepository).execute(
                AddLetter.Params(
                    State.alphabetId!!,
                    "А",
                )
            )
            val alphabet = GetAlphabet(State.alphabetRepository).execute(State.alphabetId!!)
            val letter = alphabet.letters[0]

            State.taskId =
                CreateLetterListeningTask(State.taskRepository).execute(
                    CreateLetterListeningTask.Params(
                        letter
                    )
                )
        }
        When("админ создаёт задание") {
            State.alphabetId =
                CreateAlphabet(State.alphabetRepository).execute(Alphabet.Language.ru)
            AddLetter(State.alphabetRepository).execute(
                AddLetter.Params(
                    State.alphabetId!!,
                    "А",
                )
            )
            val alphabet = GetAlphabet(State.alphabetRepository).execute(State.alphabetId!!)
            val letter = alphabet.letters[0]

            State.taskId =
                CreateLetterListeningTask(State.taskRepository).execute(
                    CreateLetterListeningTask.Params(
                        letter
                    )
                )
        }
        Then("задание появляется") {
            val task = GetTask(State.taskRepository).execute(State.taskId!!)
            assertNotNull(task)
        }
        When("админ добавляет в задание иллюстрацию") {
            AddIllustration(State.taskRepository).execute(
                AddIllustration.Params(
                    State.taskId!!,
                    "image.png",
                    "sound.mp3"
                )
            )
        }
        Then("в задании появляется иллюстрация") {
            val task = GetTask(State.taskRepository).execute(State.taskId!!)
            assertEquals(1, task.illustrations.size)
        }
        And("у задания есть иллюстрация") {
            AddIllustration(State.taskRepository).execute(
                AddIllustration.Params(
                    State.taskId!!,
                    "image.png",
                    "sound.mp3"
                )
            )
        }
        When("админ удаляет иллюстрацию") {
            RemoveIllustration(State.taskRepository).execute(
                RemoveIllustration.Params(
                    State.taskId!!,
                    "image.png",
                    "sound.mp3"
                )
            )
        }
        Then("иллюстрация пропадает из задания") {
            val task = GetTask(State.taskRepository).execute(State.taskId!!)
            assertEquals(0, task.illustrations.size)
        }
        When("админ запрашивает непривязанные к урокам задания") {
            State.unassignedTasks =
                GetUnassignedTasks(State.taskRepository, State.lessonRepository).execute(Unit)
        }
        Then("он видит список с непривязанным к урокам заданием") {
            assertNotNull(State.unassignedTasks)
            assertEquals(1, State.unassignedTasks!!.size)
            assertEquals(State.taskId, State.unassignedTasks!![0].id)
        }
    }
}