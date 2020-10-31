package ru.dopaminka.usecases.alphabet

import ru.dopaminka.entity.Alphabet
import ru.dopaminka.entity.common.Identity
import ru.dopaminka.usecases.UseCase
import ru.dopaminka.usecases.Repository

/**
 * input = lesson title
 */
class CreateAlphabet(private val alphabetRepository: Repository<Alphabet>) :
    UseCase<Alphabet.Language, Identity>() {

    override fun execute(params: Alphabet.Language): Identity {
        val id = Alphabet.idFromLanguage(params)
        val alphabet = alphabetRepository.get(id)
        if (alphabet == null) {
            alphabetRepository.save(Alphabet(id, params))
        }
        return id
    }

}