package ru.dopaminka.entity.reading

class Letter(text: String, sound: String, val shapes: List<Shape>) :
    AtomicText(text, sound)