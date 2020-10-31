package ru.dopaminka.usecases.alphabet

import ru.dopaminka.entity.Alphabet
import ru.dopaminka.entity.common.Identity
import ru.dopaminka.usecases.Repository
import ru.dopaminka.usecases.UseCase

/**
 * input = lesson title
 */
class AddLetter(
    private val alphabetRepository: Repository<Alphabet>,
) :
    UseCase<AddLetter.Params, Unit>() {

    override fun execute(params: Params) {
        val alphabet =
            alphabetRepository.get(params.alphabetId) ?: throw Exception("Alphabet not found")

        alphabet.addLetter(params.letter)
    }

    class Params(val alphabetId: Identity, val letter: String)
}