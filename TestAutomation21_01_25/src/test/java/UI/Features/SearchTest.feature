Feature: Search and Add Product to Cart

  Scenario: Search for a product and verify it in the cart
    Given I open the app and continue without login
    When I search for "Laptop"
    And I select the product "Laptop"
    Then the product title should contain "Laptop"
    When I add the product to the cart
    And I go to the cart
    Then the cart should contain "Laptop"
