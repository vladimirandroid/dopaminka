package ru.dopaminka.persistence

import androidx.annotation.RawRes
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import ru.dopaminka.entity.program.Program
import ru.dopaminka.entity.program.Task
import ru.dopaminka.entity.reading.*
import ru.dopaminka.entity.readingProgram.*
import ru.dopaminka.usecases.ProgramProvider


class ProgramProviderImpl(private val rawFileTextProvider: RawFileTextProvider) : ProgramProvider {

    private var program: Program? = null

    init {
        program = load()
    }

    override fun get() = program!!

    private fun load(): Program {
        val standardMoshi = Moshi.Builder().build()
        val alphabetAdapter = standardMoshi.adapter(Alphabet::class.java)
        val alphabet = alphabetAdapter.fromJson(rawFileTextProvider.get(R.raw.alphabet))!!


        val moshi =
            Moshi.Builder()
                .add(
                    PolymorphicJsonAdapterFactory.of(Task::class.java, "type")
                        .withSubtype(ListenTask::class.java, "listen")
                        .withSubtype(ListenAndSelectTextTask::class.java, "listenAndSelectText")
                        .withSubtype(ReadAndSelectImageTask::class.java, "readAndSelectImage")
                        .withSubtype(JoinTwoLettersTask::class.java, "joinTwoLetters")
                        .withSubtype(InsertMissingLetterTask::class.java, "insertMissingLetter")
                        .withSubtype(DrawTextTask::class.java, "drawText")
                )
                .add(
                    PolymorphicJsonAdapterFactory.of(Readable::class.java, "type")
                        .withSubtype(Letter::class.java, "letter")
                        .withSubtype(Text::class.java, "text")
                        .withSubtype(Unpronounceable::class.java, "unpronounceable")
                        .withSubtype(Word::class.java, "word")
                )
                .add(LetterAdapter(alphabet))
                .build()

        val programAdapter = moshi.adapter(Program::class.java)
        val program = programAdapter.fromJson(rawFileTextProvider.get(R.raw.program))!!


        return program
    }

}

interface RawFileTextProvider {
    fun get(@RawRes id: Int): String
}

class LetterAdapter(private val alphabet: Alphabet) {
    @ToJson
    fun toJson(letter: Letter) = letter.text

    @FromJson
    fun fromJson(letterJson: LetterJson) = alphabet.letters.first { it.text == letterJson.text }
}

class LetterJson(val type: String, val text: String)