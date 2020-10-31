Feature: алфавит

  @Admin
  Scenario: создание алфавита
    When админ создаёт алфавит для русского языка
    Then алфавит появляется
    And в алфавите нет букв
    And язык алфавита - русский

  @Admin
  Scenario: добавление букв в алфавит
    Given есть алфавит
    When админ добавляет букву в алфавит
    Then буква появляется в алфавите

  @Admin
  Scenario: удаление буквы из алфавита
    Given есть алфавит
    And есть буква в алфавите
    When админ удаляет букву из алфавита
    Then буква исчезает из алфавита

  @Admin
  Scenario: создание дубликата буквы в алфавите
    Given есть алфавит
    And есть буква в алфавите
    When админ добавляет дубликат буквы в алфавит
    Then дубликат не появился
    And произошла ошибка
