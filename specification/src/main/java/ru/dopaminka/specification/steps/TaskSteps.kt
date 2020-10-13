package ru.dopaminka.specification.steps

import io.cucumber.java.Before
import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.junit.Assert.assertNotNull
import ru.dopaminka.entity.Illustration
import ru.dopaminka.entity.Task
import ru.dopaminka.entity.common.Identity
import ru.dopaminka.specification.STATE

class TaskSteps {

    private val ILLUSTRATION = Illustration("image.jpg", "sound.mp3")

    private var task: Task
        get() = STATE["task"] as Task
        set(value) {
            STATE["task"] = value
        }

    //GIVEN:

    @Given("есть задание")
    fun thereIsATask() {
        task = object : Task(Identity.generate()) {}
    }

    @And("у задания есть иллюстрация")
    fun theTaskContainsAnIllustration() {
        task.addIllustration(ILLUSTRATION)
    }

    //WHEN:

    @When("админ создаёт задание")
    fun adminCreatesATask() {
        task = object : Task(Identity.generate()) {}
    }

    @When("админ добавляет в задание иллюстрацию")
    fun adminAddsAnIllustrationToTheTask() {
        task.addIllustration(ILLUSTRATION)
    }

    @When("админ удаляет иллюстрацию")
    fun adminRemovesTheIllustration() {
        task.removeIllustrtion(ILLUSTRATION)
    }

    //THEN:

    @Then("задание появляется")
    fun theTaskAppears() {
        assertNotNull(task)
    }

    @Then("в задании появляется иллюстрация")
    fun theIllustrationsAppearsInTheTask() {
        task.illustrations.contains(ILLUSTRATION)
    }

    @Then("иллюстрация пропадает из задания")
    fun theIllustrationDisappearsFromTheTask() {
        task.removeIllustrtion(ILLUSTRATION)
    }
}