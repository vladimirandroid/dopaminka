package ru.dopaminka.entity

class Alphabet(val language: String) {
    private val _letters: MutableSet<Letter> = mutableSetOf()
    val letters: Set<Letter>
        get() = _letters

    fun addLetter(letter: Letter) {
        if(_letters.contains(letter)){
            throw Exception("Can't add a duplicate letter to an alphabet");
        }
        _letters.add(letter)
    }

    fun removeLetter(letter: Letter) {
        _letters.remove(letter)
    }
}