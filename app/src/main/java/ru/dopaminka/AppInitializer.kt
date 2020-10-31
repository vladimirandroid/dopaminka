package ru.dopaminka

import android.util.Log
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import ru.dopaminka.entity.Alphabet
import ru.dopaminka.usecases.alphabet.AddLetter
import ru.dopaminka.usecases.alphabet.CreateAlphabet
import ru.dopaminka.usecases.alphabet.GetAlphabet
import ru.dopaminka.usecases.lesson.AddTask
import ru.dopaminka.usecases.lesson.CreateLesson
import ru.dopaminka.usecases.lesson.GetLesson
import ru.dopaminka.usecases.lesson.GetUnassignedLessons
import ru.dopaminka.usecases.program.CreateProgram
import ru.dopaminka.usecases.program.GetProgram
import ru.dopaminka.usecases.program.SetProgramLessons
import ru.dopaminka.usecases.task.AddIllustration
import ru.dopaminka.usecases.task.CreateLetterListeningTask
import ru.dopaminka.usecases.task.GetTask
import ru.dopaminka.usecases.task.GetUnassignedTasks

@KoinApiExtension
class AppInitializer : KoinComponent {
    fun initialize() {
        setUpAlphabet()
        setUpTasks()
        setUpLessons()
        setUpProgram()
    }

    private fun setUpProgram() {
        val lessons = get<GetUnassignedLessons>().execute(Unit)
        val lessonsIds = lessons.map { it.id }

        get<CreateProgram>().execute(Alphabet.Language.ru)
        get<SetProgramLessons>().execute(SetProgramLessons.Params(Alphabet.Language.ru, lessonsIds))

        val programView = get<GetProgram>().execute(GetProgram.Params(Alphabet.Language.ru))
        Log.d(TAG, "Program = $programView")
    }

    private fun setUpLessons() {
        val tasks = get<GetUnassignedTasks>().execute(Unit)

        val lessonId = get<CreateLesson>().execute(CreateLesson.Params("Первый урок"))
        get<AddTask>().execute(AddTask.Params(lessonId, tasks.first().id))

        val lesson = get<GetLesson>().execute(lessonId)
        Log.d(TAG, "Lesson = $lesson")
    }

    private fun setUpTasks() {
        val alphabet = get<GetAlphabet>().execute(Alphabet.idFromLanguage(Alphabet.Language.ru))

        val createTask: CreateLetterListeningTask = get()
        val taskId = createTask.execute(CreateLetterListeningTask.Params(alphabet.letters[0]))

        get<AddIllustration>().execute(AddIllustration.Params(taskId, "01.mp3", "char_01.png"))

        val taskView = get<GetTask>().execute(taskId)
        Log.d(TAG, "Task = $taskView");
    }

    private fun setUpAlphabet() {
        val createAlphabet: CreateAlphabet = get()
        createAlphabet.execute(Alphabet.Language.ru)

        val letters = arrayOf(
            "А",
            "Б",
            "В",
            "Г",
            "Д",
            "Е",
            "Ё",
            "Ж",
            "З",
            "И",
            "Й",
            "К",
            "Л",
            "М",
            "Н",
            "О",
            "П",
            "Р",
            "С",
            "Т",
            "У",
            "Ф",
            "Х",
            "Ц",
            "Ч",
            "Ш",
            "Щ",
            "Ъ",
            "Ы",
            "Ь",
            "Э",
            "Ю",
            "Я"
        )
        for (i in 0..32) {
            val addLetter: AddLetter = get()
            addLetter.execute(
                AddLetter.Params(
                    Alphabet.idFromLanguage(Alphabet.Language.ru),
                    letters[i],
                )
            )
        }

        val alphabetView = get<GetAlphabet>().execute(Alphabet.idFromLanguage(Alphabet.Language.ru))
        Log.d("TAG", "Alphabet = $alphabetView")
    }

    companion object {
        const val TAG = "initialize"
    }
}