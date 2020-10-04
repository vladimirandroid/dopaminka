Feature: работа с заданиями

  @Admin
  Scenario: Создание урока
    When админ создает урок
    Then урок появляется

  @Admin
  Scenario: Удаление урока
    Given есть урок
    When админ удаляет урок
    Then урок удаляется

  @Admin
  Scenario: Добавление задания
    Given есть урок
    When админ добавляет задание в урок
    Then в уроке появляется новое задание

  @Admin
  Scenario: Удаление задания
    Given есть урок
    And в уроке есть задание
    When админ удаляет задание из урока
    Then задание удаляется из урока

  @Student
  Scenario Outline: завершение урока при завершении всех его заданий
    Given есть урок
    And в уроке есть <number of tasks> заданий
    When ученик завершает <number of completed tasks> заданий
    Then пройденные задания отмечаются как пройденные
    And урок переходит в статус <lesson state>
    Examples:
      | number of tasks | number of completed tasks | lesson state  |
      | 2               | 1                         | completed     |
      | 2               | 2                         | not completed |