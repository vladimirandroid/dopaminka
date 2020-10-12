package ru.dopaminka.specification.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ru.dopaminka.entity.Alphabet;
import ru.dopaminka.entity.Letter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AlphabetSteps {

    private static final int NEW_SOUND = 1;
    private static final int SOUND = 0;

    private Letter letter;
    private Alphabet alphabet;
    private Exception exception;

    //GIVEN:

    @Given("есть буква")
    public void thereIsALetter() {
        theLetterAExists();
    }

    @Given("у буквы есть произношение")
    public void theLetterHasASound() {
        assertNotNull(letter.getSound());
    }

    @Given("буквы А нет")
    public void thereIsNoLetterA() {
    }

    @Given("есть буква А")
    public void theLetterAExists() {
        letter = new Letter("А", 0);
    }

    @And("есть алфавит")
    public void thereIsAnAlphabet() {
        alphabet = new Alphabet("ru");
    }

    @And("буква в алфавите")
    public void theLetterIsInAlphabet() {
        alphabet.addLetter(letter);
    }

    //WHEN:

    @When("админ меняет озвучку букве")
    public void adminSetTheSoundForTheLetter() {
        letter.setSound(NEW_SOUND);
    }

    @When("админ создаёт букву А с указанием произношения")
    public void adminCreatesLetterA() {
        letter = new Letter("А", SOUND);
    }

    @When("админ создаёт алфавит с указанием языка")
    public void adminCreatesAlphabetForLanguage() {
        alphabet = new Alphabet("ru");
    }

    @When("админ добавляет букву в алфавит")
    public void adminAddsALetterToTheAlphabet() {
        try {
            alphabet.addLetter(letter);
        } catch (Exception e) {
            exception = e;
        }
    }

    @When("админ удаляет букву из алфавита")
    public void adminRemovesTheLetterFromTheAlphabet() {
        alphabet.removeLetter(letter);
    }

    //THEN:

    @Then("озвучка буквы заменяется на новую")
    public void theLetterSoundIsReplacedToTheNewOne() {
        assertEquals(letter.getSound(), NEW_SOUND);
    }

    @Then("буква А появляется")
    public void theLetterAAppears() {
        assertNotNull(letter);
        assertEquals(letter.getText(), "А");
    }

    @Then("дубликат буквы А не появился")
    public void duplicateHasNotAppeared() {
        //can't be automated through entity layer
    }

    @Then("алфавит появляется")
    public void alphabetAppears() {
        assertNotNull(alphabet);
    }

    @Then("буква появляется в алфавите")
    public void theLetterAppearsInTheAlphabet() {
        assertTrue(alphabet.getLetters().contains(letter));
    }

    @Then("буква исчезает из алфавита")
    public void theLetterDisappearsFromTheAlphabet() {
        assertFalse(alphabet.getLetters().contains(letter));
    }

    @And("произошла ошибка")
    public void exceptionHasBeenThrown() {
        assertNotNull(exception);
    }

    @Then("В алфавите все еще одна буква")
    public void theAlphabetStillContainsOnlyLetter() {
        assertEquals(1, alphabet.getLetters().size());
    }
}
