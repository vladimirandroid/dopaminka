package ru.dopaminka.usecases.lesson

import ru.dopaminka.entity.Lesson
import ru.dopaminka.entity.common.Identity
import ru.dopaminka.usecases.UseCase
import ru.dopaminka.usecases.repository.Repository

/**
 * input = lesson title
 */
class CreateLesson(private val lessonRepository: Repository<Lesson>) :
    UseCase<CreateLesson.Params, Identity>() {

    override fun execute(params: Params): Identity {
        val id = Identity.generate()
        val lesson = Lesson(id, params.title)
        lessonRepository.save(lesson)
        return id
    }

    class Params(val title: String)
}