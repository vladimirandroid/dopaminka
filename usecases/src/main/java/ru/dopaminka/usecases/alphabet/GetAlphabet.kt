package ru.dopaminka.usecases.alphabet

import ru.dopaminka.entity.Alphabet
import ru.dopaminka.entity.common.Identity
import ru.dopaminka.usecases.Repository
import ru.dopaminka.usecases.UseCase

/**
 * input = lesson title
 */
class GetAlphabet(
    private val alphabetRepository: Repository<Alphabet>,
) :
    UseCase<Identity, GetAlphabet.AlphabetView>() {

    override fun execute(params: Identity): AlphabetView {
        val alphabet = alphabetRepository.get(params) ?: throw Exception("Alphabet not found")

        return AlphabetView(
            params,
            alphabet.language,
            alphabet.letters.toList()
        )
    }

    data class AlphabetView(
        val id: Identity,
        val language: Alphabet.Language,
        val letters: List<String>
    )
}