Feature: задачи

  @Admin
  Scenario: создание задания
    When админ создаёт задание
    Then задание появляется

  @Admin
  Scenario: добавление иллюстрации
    Given есть задание
    When админ добавляет в задание иллюстрацию
    Then в задании появляется иллюстрация

  @Admin
  Scenario: удаление иллюстрации
    Given есть задание
    And у задания есть иллюстрация
    When админ удаляет иллюстрацию
    Then иллюстрация пропадает из задания