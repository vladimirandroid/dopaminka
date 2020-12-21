package ru.dopaminka.entity.reading

class Syllable(text: String, sound: String, val letters: List<Letter>) :
    AtomicText(text, sound)