package ru.dopaminka.usecases.program

import ru.dopaminka.entity.Lesson
import ru.dopaminka.entity.Program
import ru.dopaminka.entity.common.Identity
import ru.dopaminka.usecases.UseCase
import ru.dopaminka.usecases.repository.Repository

/**
 * Input is language code like "ru", "en", "fr" and so on
 */
class SetProgramLessons(
    private val programRepository: Repository<Program>,
    private val lessonRepository: Repository<Lesson>
) : UseCase<SetProgramLessons.Params, Unit>() {
    override fun execute(params: Params) {
        val program = programRepository.get(Identity(params.language))
            ?: throw Exception("Program not found")

        //remove not existing lessons
        val lessons = lessonRepository.get(params.lessonsIdentities)
        val lessonsIdentities = lessons.map { it.id }

        program.setLessons(lessonsIdentities)
        programRepository.save(program)
    }

    class Params(val language: String, val lessonsIdentities: List<Identity>)
}