package ru.dopaminka.usecases.program

import ru.dopaminka.entity.Program
import ru.dopaminka.entity.common.Identity
import ru.dopaminka.usecases.Repository
import ru.dopaminka.usecases.UseCase

/**
 * Input is language code like "ru", "en", "fr" and so on
 */
class CreateProgram(private val programRepository: Repository<Program>) :
    UseCase<Unit, Identity>() {
    override fun execute(params: Unit): Identity {
        val programs = programRepository.all()
        if (programs.isNotEmpty()) return programs.first().id

        val program = Program(Identity.generate())
        programRepository.save(program)
        return program.id
    }
}