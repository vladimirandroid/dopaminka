package ru.dopaminka

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import ru.dopaminka.entity.Alphabet
import ru.dopaminka.usecases.alphabet.CreateAlphabet
import ru.dopaminka.usecases.lesson.GetLesson
import ru.dopaminka.usecases.program.GetProgram

class LetterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_letter)

        val program = get<GetProgram>().execute(GetProgram.Params(Alphabet.Language.ru))
        val lesson = get<GetLesson>().execute(program.lessons.first().id)
        Log.d(this.localClassName, "lesson = $lesson");
    }
}