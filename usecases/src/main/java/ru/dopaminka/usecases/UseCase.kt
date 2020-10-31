package ru.dopaminka.usecases

abstract class UseCase<Parameters, Result> {
    abstract fun execute(params: Parameters): Result
}