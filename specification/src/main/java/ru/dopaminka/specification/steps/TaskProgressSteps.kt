package ru.dopaminka.specification.steps

import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.junit.Assert.assertNotNull
import ru.dopaminka.entity.Task
import ru.dopaminka.entity.TaskProgress
import ru.dopaminka.specification.STATE

class TaskProgressSteps {

    private var task: Task
        get() = STATE["task"] as Task
        set(value) {
            STATE["task"] = value
        }

    private lateinit var taskProgress: TaskProgress

    //GIVEN:


    //WHEN:

    @When("ученик завершает задание")
    fun studentCompletesTheTask() {
        taskProgress = task.complete()
    }


    //THEN:

    @Then("задание помечается как завершенное")
    fun заданиеПомечаетсяКакЗавершенное() {
        assertNotNull(taskProgress)
    }

}