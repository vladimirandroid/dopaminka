package ru.dopaminka.usecases.alphabet

import ru.dopaminka.entity.Alphabet
import ru.dopaminka.entity.common.Identity
import ru.dopaminka.usecases.UseCase
import ru.dopaminka.usecases.repository.Repository

/**
 * input = lesson title
 */
class RemoveLetter(
    private val alphabetRepository: Repository<Alphabet>,
) :
    UseCase<RemoveLetter.Params, Unit>() {

    override fun execute(params: Params) {
        val alphabet =
            alphabetRepository.get(params.alphabetId) ?: throw Exception("Alphabet not found")

        alphabet.removeLetter(Alphabet.Letter(params.text, params.soundFileName))
    }

    class Params(val alphabetId: Identity, val text: String, val soundFileName: String)
}