# Java BDD with Selenium
A complete Maven example using Cucumber, Selenium, and the JUnit Platform suite runner.
Dependency versions are pinned for reproducibility, rather than tracking latest releases.

## Requirements
- JDK 17 or newer; JAVA_HOME must point to the JDK.
- Maven 3.9 or newer.
- Installed Google Chrome (default) or Microsoft Edge.
- Internet access on the first run for Maven dependencies and Selenium Manager's driver resolution.

## Run
Open a terminal in this directory:
```sh
mvn test
```
Three scenarios run headlessly against the bundled local HTML demo. No web server or account is required.
The demo checks credentials in client-side JavaScript solely as a test fixture.

Show the browser:
```sh
mvn test -Dheadless=false
```
Use Edge:
```sh
mvn test -Dbrowser=edge
```
Only run the smoke scenario (quote the entire argument in PowerShell): you will need to uncomment the smoke section in login.feature
```sh
mvn test "-Dcucumber.filter.tags=@smoke"
```

## Reports
After a run, open target/cucumber-report.html. JSON is in target/cucumber-report.json;
Surefire results are under target/surefire-reports. Failed scenarios attach a screenshot when available.

## How it fits together
- src/test/resources/features/login.feature: Given/When/Then scenarios.
- src/test/java/com/example/runners/RunCucumberTest.java: JUnit Platform suite that selects Cucumber.
- src/test/java/com/example/steps/LoginSteps.java: scenario-scoped browser lifecycle, step definitions, assertions.
- src/test/java/com/example/pages/LoginPage.java: selectors and browser operations, using explicit waits.
- src/test/resources/demo/login.html: standalone demonstration fixture.
Each scenario gets a fresh browser, closed in the After hook.

## Test your application
Run `mvn test "-DbaseUrl=http://localhost:8080/login"`.
Change selectors in LoginPage, feature credentials, and expected dashboard text in LoginSteps to match your app.
baseUrl is the full login page URL. The existing scenarios assume the demo's messages and element IDs.

## References
- https://github.com/cucumber/cucumber-jvm/blob/main/cucumber-junit-platform-engine/README.md
- https://www.selenium.dev/documentation/webdriver/

