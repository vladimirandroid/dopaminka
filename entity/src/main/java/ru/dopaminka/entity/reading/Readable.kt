package ru.dopaminka.entity.reading

import java.io.Serializable
import kotlin.math.pow

//interface Readable : Serializable


sealed class Readable : Serializable

class Letter(text: String, sound: String, val shapes: List<Shape>) : AtomicText(text, sound)
class Syllable(text: String, sound: String, val letters: List<Letter>) : AtomicText(text, sound)
class Word(val syllables: List<Syllable>) : Readable()
class Text(val readables: List<Readable>) : Readable()
class Unpronounceable(val text: String) : Readable()
abstract class AtomicText(val text: String, val sound: String) : Readable()


data class Shape(val points: List<Point>)
data class Point(val x: Float, val y: Float) {
    fun distanceTo(point: Point) = ((x - point.x).pow(2) + (y - point.y).pow(2)).pow(0.5f)
}