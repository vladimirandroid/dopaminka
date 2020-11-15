package ru.dopaminka.usecases.program

import ru.dopaminka.entity.Lesson
import ru.dopaminka.entity.Program
import ru.dopaminka.entity.common.Identity
import ru.dopaminka.usecases.Repository
import ru.dopaminka.usecases.UseCase

/**
 * Input is language code like "ru", "en", "fr" and so on
 */
class SetLessons(
    private val programRepository: Repository<Program>,
    private val lessonRepository: Repository<Lesson>
) : UseCase<SetLessons.Params, Unit>() {
    override fun execute(params: Params) {
        val programs = programRepository.all()
        if (programs.isEmpty()) throw Exception("Program not found")

        val program = programs.single()

        //remove not existing lessons
        val lessons = lessonRepository.get(params.lessonsIdentities)
        val lessonsIdentities = lessons.map { it.id }

        program.setLessons(lessonsIdentities)
        programRepository.save(program)
    }

    class Params(val lessonsIdentities: List<Identity>)
}