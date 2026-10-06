Feature: Login
  Users can access the dashboard with valid credentials.

        # @smoke
        # Scenario: Successful login
        #     Given I am on the login page
        #      When I log in with username "demo" and password "secret"
        #      Then I should see the dashboard

        Scenario Outline: Invalid credentials are rejected
            Given I am on the login page
             When I log in with username "<username>" and password "<password>"
             Then I should see the login result "<result>"

    Examples:
      | username | password | result  |
      | demo     | secret   | success |
      | demo     | wrong    | failure |
      | unknown  | secret   | failure |

