package ru.dopaminka.specification

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    plugin = ["pretty"],
    features = ["src/main/raw/features"],
    glue = ["ru.dopaminka.specification.steps"]
)

class RunCucumberTest {
}
