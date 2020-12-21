//package ru.dopaminka.specification.steps.readingProgram
//
//import io.cucumber.java8.En
//import org.koin.core.component.KoinApiExtension
//import org.koin.core.component.KoinComponent
//import org.koin.core.component.inject
//import ru.dopaminka.entity.readingProgram.ListenAndSelectTextTask
//import ru.dopaminka.specification.State
//import ru.dopaminka.usecases.task.AnswerToListenAndSelectTextTask
//
//@KoinApiExtension
//class ListenAndSelectTextTaskSteps : En, KoinComponent {
//
//    private val state: State by inject()
//
//    private val getTask: GetTask by inject()
//    private val answerToListenAndSelectTextTask: AnswerToListenAndSelectTextTask by inject()
//
//    init {
//        When("ученик отвечает правильно на задание _прослушать_и_выбрать_текст_") {
//            val task = getTask.execute(state.taskId!!)
//            val t = task as ListenAndSelectTextTask
//            answerToListenAndSelectTextTask.execute(
//                AnswerToListenAndSelectTextTask.Params(
//                    state.lessonId!!,
//                    state.taskId!!,
//                    t.rightText
//                )
//            )
//        }
//        When("ученик отвечает неправильно на задание _прослушать_и_выбрать_текст_") {
//            val task = getTask.execute(state.taskId!!)
//            val t = task as ListenAndSelectTextTask
//            answerToListenAndSelectTextTask.execute(
//                AnswerToListenAndSelectTextTask.Params(
//                    state.lessonId!!,
//                    state.taskId!!,
//                    t.wrongText
//                )
//            )
//        }
//    }
//}