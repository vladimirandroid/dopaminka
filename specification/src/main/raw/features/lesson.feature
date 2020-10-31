Feature: работа с уроками

  @Admin
  Scenario: Создание урока
    When админ создает урок
    Then урок появляется
    And в уроке нет заданий

  @Admin
  Scenario: Удаление урока
    Given есть урок
    When админ удаляет урок
    Then урок удаляется

  @Admin
  Scenario: Добавление задания
    Given есть урок
    And есть задание
    When админ добавляет задание в урок
    Then в уроке появляется задание

  @Admin
  Scenario: Удаление задания
    Given есть урок
    And есть задание
    And задание добавлено в урок
    When админ удаляет задание из урока
    And в уроке нет заданий

  @Admin
  Scenario: получение непривязанных к программам уроков
    Given есть урок
    When админ запрашивает непривязанные к программам уроки
    Then он видит список с непривязанным к программам уроком