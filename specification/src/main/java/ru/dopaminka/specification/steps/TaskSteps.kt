package ru.dopaminka.specification.steps

import io.cucumber.java8.En
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dopaminka.entity.Alphabet
import ru.dopaminka.specification.State
import ru.dopaminka.usecases.alphabet.AddLetter
import ru.dopaminka.usecases.alphabet.CreateAlphabet
import ru.dopaminka.usecases.alphabet.GetAlphabet
import ru.dopaminka.usecases.task.*

@KoinApiExtension
class TaskSteps : En, KoinComponent {

    private val state: State by inject()
    private val createAlphabet: CreateAlphabet by inject()
    private val addLetter: AddLetter by inject()
    private val getAlphabet: GetAlphabet by inject()
    private val createLetterListeningTask: CreateLetterListeningTask by inject()
    private val getTask: GetTask by inject()
    private val addIllustration: AddIllustration by inject()
    private val removeIllustration: RemoveIllustration by inject()
    private val getUnassignedTasks: GetUnassignedTasks by inject()

    init {
        And("есть задание") {
            state.alphabetId = createAlphabet.execute(Alphabet.Language.ru)
            addLetter.execute(AddLetter.Params(state.alphabetId!!, "А"))
            val alphabet = getAlphabet.execute(state.alphabetId!!)
            val letter = alphabet.letters[0]

            state.taskId =
                createLetterListeningTask.execute(CreateLetterListeningTask.Params(letter))
        }
        When("админ создаёт задание") {
            state.alphabetId = createAlphabet.execute(Alphabet.Language.ru)
            addLetter.execute(AddLetter.Params(state.alphabetId!!, "А"))
            val alphabet = getAlphabet.execute(state.alphabetId!!)
            val letter = alphabet.letters[0]

            state.taskId =
                createLetterListeningTask.execute(CreateLetterListeningTask.Params(letter))
        }
        Then("задание появляется") {
            val task = getTask.execute(state.taskId!!)
            assertNotNull(task)
        }
        When("админ добавляет в задание иллюстрацию") {
            addIllustration.execute(
                AddIllustration.Params(
                    state.taskId!!,
                    "image.png",
                    "sound.mp3"
                )
            )
        }
        Then("в задании появляется иллюстрация") {
            val task = getTask.execute(state.taskId!!)
            assertEquals(1, task.illustrations.size)
        }
        And("у задания есть иллюстрация") {
            addIllustration.execute(
                AddIllustration.Params(
                    state.taskId!!,
                    "image.png",
                    "sound.mp3"
                )
            )
        }
        When("админ удаляет иллюстрацию") {
            removeIllustration.execute(
                RemoveIllustration.Params(
                    state.taskId!!,
                    "image.png",
                    "sound.mp3"
                )
            )
        }
        Then("иллюстрация пропадает из задания") {
            val task = getTask.execute(state.taskId!!)
            assertEquals(0, task.illustrations.size)
        }
        When("админ запрашивает непривязанные к урокам задания") {
            state.unassignedTasks = getUnassignedTasks.execute(Unit)
        }
        Then("он видит список с непривязанным к урокам заданием") {
            assertNotNull(state.unassignedTasks)
            assertEquals(1, state.unassignedTasks!!.size)
            assertEquals(state.taskId, state.unassignedTasks!![0].id)
        }
    }
}