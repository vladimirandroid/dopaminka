//package ru.dopaminka.specification.steps.program
//
//import io.cucumber.java8.En
//import org.junit.Assert.*
//import org.koin.core.component.KoinApiExtension
//import org.koin.core.component.KoinComponent
//import org.koin.core.component.inject
//import ru.dopaminka.specification.State
//import ru.dopaminka.usecases.ProgressProvider
//
//@KoinApiExtension
//class LessonProgressSteps : En, KoinComponent {
//
//    private val state: State by inject()
//    private val progressProvider: ProgressProvider by inject()
//
//    init {
//        Then("задание помечается как завершенное") {
//            progressProvider.progress
//            state.pro = getLessonTasks.execute(state.lessonId!!)
//            assertTrue(state.lessonTasksView!!.tasks[0].isCompleted)
//        }
//        Then("задание не помечается как завершенное") {
//            state.lessonTasksView = getLessonTasks.execute(state.lessonId!!)
//            assertFalse(state.lessonTasksView!!.tasks[0].isCompleted)
//        }
//        And("прогресс урока становится = {double}") { progress: Double ->
//            assertEquals(progress, state.lessonTasksView!!.progress, 0.0001)
//        }
//    }
//}