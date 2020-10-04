Feature: арифметические операции
  Scenario: сложение
    Given А=5
    And Б=6
    When складываю А и Б
    Then получаю 11

  Scenario: умножение
    Given А=5
    And Б=6
    When умножаю А и Б
    Then получаю 30