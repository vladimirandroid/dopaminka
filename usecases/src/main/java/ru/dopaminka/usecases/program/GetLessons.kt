package ru.dopaminka.usecases.program

import ru.dopaminka.entity.Lesson
import ru.dopaminka.entity.Program
import ru.dopaminka.entity.common.Identity
import ru.dopaminka.usecases.Repository
import ru.dopaminka.usecases.UseCase

class GetLessons(
    private val programRepository: Repository<Program>,
    private val lessonRepository: Repository<Lesson>
) :
    UseCase<Unit, List<GetLessons.LessonView>>() {

    override fun execute(params: Unit): List<LessonView> {
        val programs = programRepository.all()
        if (programs.isEmpty()) throw Exception("The program not found")

        val program = programs.single()

        val lessons = lessonRepository.get(program.lessonsIds).map {
            LessonView(it.id, it.title)
        }

        return lessons
    }

    data class LessonView(
        val id: Identity,
        val title: String,
    )
}