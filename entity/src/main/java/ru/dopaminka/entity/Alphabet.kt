package ru.dopaminka.entity

import ru.dopaminka.entity.common.Entity
import ru.dopaminka.entity.common.Identity

class Alphabet(id: Identity, val language: Language) : Entity(id) {
    private val _letters: MutableSet<String> = mutableSetOf()
    val letters: Set<String>
        get() = _letters

    fun addLetter(letter: String) {
        if (_letters.contains(letter)) {
            throw Exception("Can't add a duplicate letter to an alphabet");
        }
        _letters.add(letter)
    }

    fun removeLetter(letter: String) {
        _letters.remove(letter)
    }

    override fun toString(): String {
        return "Alphabet(language=$language, letters=$letters)"
    }

    enum class Language {
        ru
    }

    companion object {
        fun idFromLanguage(language: Language): Identity = Identity(language.toString())
    }
}