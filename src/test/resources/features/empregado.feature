Feature: Empregado

  @api
  Scenario: Validar criação de um empregado
    Given que efetuo o cadastro de um novo empregado valido
    When verifico o retorno
    Then devo obter o retorno de sucesso
    And dados são gravados corretamente