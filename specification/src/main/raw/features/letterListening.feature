Feature: настройка задания ПрослушиваниеБуквы

  @Admin
  Scenario: Создание задания ПрослушиваниеБуквы
    Given есть алфавит
    And есть буква в алфавите
    When админ создает задание ПрослушиваниеБуквы
    Then появляется задание ПрослушиваниеБуквы