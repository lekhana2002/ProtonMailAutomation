**Architecture Note – Proton Mail Automation Testing**

**1. Project Overview**

This project automates important functionalities of the Proton Mail web application using Selenium WebDriver with Java and TestNG. The purpose is to verify application behavior and identify defects in the tested scenarios.

**2. Framework Structure**

- **Test Class:** `ProtonMailFuntionalTesting.java` contains the test methods for different functionalities, such as login validation, composing emails, drafts, search, filters, attachments, and Undo Send.
- **Page Object Classes:** Separate page classes contain web element locators and reusable actions for the corresponding pages. This helps reduce duplicate code and makes maintenance easier.
- **TestNG:** TestNG is used to execute test methods, organize test execution, and display test results.
- **testng.xml:** This file specifies the test class or classes to execute as a TestNG suite.
- **README.md:** Provides project details, setup requirements, and instructions for running the tests.
- **Test Reports:** TestNG generates execution reports after the test suite runs.

**3. Important Design Decisions**

- **Selenium WebDriver with Java:** Selected to automate browser interactions and validate web application functionality.
- **Page Object Model (POM):** Used to separate page actions and element locators from test scenarios, where implemented.
- **TestNG:** Used to organize and execute test cases and review pass/fail results.
- **Explicit Waits:** Used where required to wait for web elements to become available or clickable, helping reduce synchronization issues.
- **Functional Test Coverage:** Tests cover important workflows, including login validation, email composition, drafts, inbox actions, search, filters, attachments, and Undo Send.
- **Assertions:** Used to compare actual behavior with expected results.

**4. Test Execution Flow**

1. TestNG reads the test configuration from `testng.xml`.
2. Selenium WebDriver opens the browser.
3. Test methods perform the required actions on Proton Mail.
4. Assertions validate the expected behavior.
5. TestNG records the test results and generates reports.

**5. Limitations**

The automation results depend on internet connectivity, application changes, test account data, and the implemented test scenarios. Only the functionalities covered by the executed tests have been evaluated.

**Conclusion**

The framework uses Java, Selenium WebDriver, TestNG, and Page Object Model principles to automate important Proton Mail workflows. The structure helps organize test cases, reuse page actions, and review test execution results.
