Feature: настройка задания ПрослушиваниеБуквы

  @Admin
  Scenario: Создание задания ПрослушиваниеБуквы
    Given есть буква А
    When админ создает задание ПрослушиваниеБуквы для буквы А
    Then появляется задание ПрослушиваниеБуквы А