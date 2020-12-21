package ru.dopaminka.specification

import ru.dopaminka.entity.program.Program
import ru.dopaminka.usecases.ProgramProvider

class TestProgramProvider : ProgramProvider {

    lateinit var program: Program

    override fun get() = program
}