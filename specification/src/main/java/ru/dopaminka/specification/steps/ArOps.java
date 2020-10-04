package ru.dopaminka.specification.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

public class ArOps {

    int a;
    int b;
    int result;

    @Given("^А=(\\d+)$")
    public void aEqual(int a) {
        this.a = a;
    }

    @Given("^Б=(\\d+)$")
    public void bEqual(int b) {
        this.b = b;
    }

    @When("^складываю А и Б$")
    public void add() {
        result = a + b;
    }

    @When("^умножаю А и Б$")
    public void mult() {
        result = a * b;
    }

    @Then("^получаю (\\d+)$")
    public void get(int res) {
        assertEquals(res, result);
    }
}
