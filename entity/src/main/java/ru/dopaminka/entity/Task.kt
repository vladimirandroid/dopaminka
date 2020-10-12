package ru.dopaminka.entity

abstract class Task

var completed: Boolean = false
lateinit var illustrations: Illustration


fun complete() {}
fun addIllustration() {}
fun removeillustrtion() {}