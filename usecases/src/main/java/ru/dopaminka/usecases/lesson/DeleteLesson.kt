package ru.dopaminka.usecases.lesson

import ru.dopaminka.entity.Lesson
import ru.dopaminka.entity.common.Identity
import ru.dopaminka.usecases.UseCase
import ru.dopaminka.usecases.repository.Repository

/**
 * input = lesson title
 */
class DeleteLesson(private val lessonRepository: Repository<Lesson>) :
    UseCase<Identity, Unit>() {

    override fun execute(params: Identity) {
        lessonRepository.remove(params)
    }
}