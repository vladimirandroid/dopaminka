package ru.dopaminka.specification.steps

import io.cucumber.java.Before
import ru.dopaminka.specification.STATE

class Hooks {
    @Before
    fun setUp() {
        println("setUp " + STATE.hashCode())
        STATE.clear()
    }
}