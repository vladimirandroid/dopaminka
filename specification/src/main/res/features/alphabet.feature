Feature: алфавит

  @Business
  @Admin
  Scenario: создание алфавита
    When админ создаёт алфавит с указанием языка
    Then алфавит появляется

  @Admin
  Scenario: добавление букв в алфавит
    Given есть буква
    And есть алфавит
    When админ добавляет букву в алфавит
    Then буква появляется в алфавите

  @Admin
  Scenario: добавление букв в алфавит
    Given есть буква
    And есть алфавит
    And буква в алфавите
    When админ удаляет букву из алфавита
    Then буква исчезает из алфавита

  @Admin
  Scenario: создание дубликата буквы в алфавите
    Given есть буква А
    And есть алфавит
    And буква в алфавите
    When админ добавляет букву в алфавит
    Then В алфавите все еще одна буква
    And произошла ошибка

  @Business
  @Student
  Scenario: замена озвучки буквы
    Given есть буква А
    And у буквы есть произношение
    When админ меняет озвучку букве
    Then озвучка буквы заменяется на новую

  @Business
  @Admin
  Scenario: создание буквы
    Given буквы А нет
    When админ создаёт букву А с указанием произношения
    Then буква А появляется
    And у буквы есть произношение

  @Business
  @Student
  Scenario: замена озвучки
    Given есть буква А
    And у буквы есть произношение
    When админ меняет озвучку букве
    Then озвучка буквы заменяется на новую