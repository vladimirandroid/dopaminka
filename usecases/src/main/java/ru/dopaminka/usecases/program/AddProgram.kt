package ru.dopaminka.usecases.program

import ru.dopaminka.entity.Alphabet
import ru.dopaminka.entity.Program
import ru.dopaminka.entity.common.Identity
import ru.dopaminka.usecases.UseCase
import ru.dopaminka.usecases.repository.Repository

/**
 * Input is language code like "ru", "en", "fr" and so on
 */
class AddProgram(private val programRepository: Repository<Program>) :
    UseCase<Alphabet.Language, Identity>() {
    override fun execute(params: Alphabet.Language): Identity {
        val identity = Alphabet.idFromLanguage(params)
        var program = programRepository.get(identity)
        if (program == null) {
            program = Program(identity, params)
            programRepository.save(program)
        }
        return identity
    }
}