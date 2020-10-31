package ru.dopaminka.entity

import ru.dopaminka.entity.common.Entity
import ru.dopaminka.entity.common.Identity

class Alphabet(id: Identity, val language: Language) : Entity(id) {
    private val _letters: MutableSet<Letter> = mutableSetOf()
    val letters: Set<Letter>
        get() = _letters

    fun addLetter(letter: Letter) {
        if (_letters.contains(letter)) {
            throw Exception("Can't add a duplicate letter to an alphabet");
        }
        _letters.add(letter)
    }

    fun removeLetter(letter: Letter) {
        _letters.remove(letter)
    }

    class Letter(var text: String, var soundFileName: String) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Letter

            if (text != other.text) return false

            return true
        }

        override fun hashCode(): Int {
            return text.hashCode()
        }
    }

    enum class Language {
        ru
    }

    companion object {
        fun idFromLanguage(language: Language): Identity = Identity(language.toString())
    }
}