
@tag
Feature: Purchase the order from eCommerce website
  I want to use this template for my feature file

	Background: 
	Given  I landed on Ecommerce Page
	
  @Regression
  Scenario Outline: Positive test for submitting the order #Scenario Outline is when a table of parameters are passed
    Given Logged in with <username> and <password>
    When I want to add <productName> to Cart
    And Checkout <productName> and submit the order
    Then "THANKYOU FOR THE ORDER." message is displayed on ConfirmationPage

    Examples: 
      | username             | password    | productName |
      | tonystark@gmail.com  | Cognizant1* | ZARA COAT 3 |
      #| tonystark1@gmail.com | Cognizant1  | ADIDAS ORIGINAL |
