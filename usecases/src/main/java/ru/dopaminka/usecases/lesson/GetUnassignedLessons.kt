package ru.dopaminka.usecases.lesson

import ru.dopaminka.entity.Lesson
import ru.dopaminka.entity.Program
import ru.dopaminka.entity.common.Identity
import ru.dopaminka.usecases.Repository
import ru.dopaminka.usecases.UseCase

/**
 * input = lesson title
 */
class GetUnassignedLessons(
    private val programRepository: Repository<Program>,
    private val lessonRepository: Repository<Lesson>
) :
    UseCase<Unit, List<GetUnassignedLessons.LessonView>>() {

    override fun execute(params: Unit): List<LessonView> {
        val programs = programRepository.all()
        val assignedLessonsIds = mutableSetOf<Identity>()
        programs.forEach { assignedLessonsIds.addAll(it.lessonsIds) }

        val allLessons = lessonRepository.all()
        val unassignedLessons = allLessons.filter { !assignedLessonsIds.contains(it.id) }

        return unassignedLessons.map {
            LessonView(it.id, it.title)
        }
    }

    data class LessonView(
        val id: Identity,
        val title: String,
    )

}