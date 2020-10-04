Feature: работа с заданиями

  @Admin
  Scenario: создание задания
    When админ создаёт задание
    Then задание появляется

  @Admin
  Scenario: добавление иллюстрации
    Given есть задание
    When админ добавляет в него иллюстрацию
    Then в задании появляется иллюстрация

  @Admin
  Scenario: удаление иллюстрации
    Given есть задание
    And у задания есть иллюстрация
    When админ удаляет иллюстрацию
    Then иллюстрация пропадает из задания

  @Student
  Scenario: завершение задания
    Given есть задание
    When ученик завершает его
    Then задание помечается как пройденное