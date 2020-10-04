package ru.dopaminka.specification;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty"},
        features = {"src/main/res/features/*"},
        glue = {"ru.dopaminka.specification.steps.*"}
)
public class RunCucumberTest {
}