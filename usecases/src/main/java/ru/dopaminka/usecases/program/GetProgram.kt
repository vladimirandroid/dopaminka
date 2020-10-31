package ru.dopaminka.usecases.program

import ru.dopaminka.entity.Alphabet
import ru.dopaminka.entity.Lesson
import ru.dopaminka.entity.Program
import ru.dopaminka.entity.common.Identity
import ru.dopaminka.usecases.Repository
import ru.dopaminka.usecases.UseCase

class GetProgram(
    private val programRepository: Repository<Program>,
    private val lessonRepository: Repository<Lesson>
) :
    UseCase<GetProgram.Params, GetProgram.ProgramView>() {

    override fun execute(params: Params): ProgramView {
        val identity = Identity(params.language.toString())

        val program = programRepository.get(identity)
            ?: throw Exception("The program not found")

        val lessons = lessonRepository.get(program.lessonsIds).map {
            LessonView(it.id, it.title)
        }

        return ProgramView(identity, params.language.toString(), lessons)
    }

    class Params(val language: Alphabet.Language)

    data class ProgramView(val id: Identity, val language: String, val lessons: List<LessonView>)

    data class LessonView(
        val id: Identity,
        val title: String,
    )
}