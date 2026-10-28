**Proton Mail Web Application Automation Testing**

**Project Overview**

This project tests the Proton Mail web application using Selenium WebDriver with Java and TestNG. The objective is to verify important application functionalities through automation testing.

**Tools and Technologies**

- Java
- Selenium WebDriver
- TestNG
- Eclipse IDE
- Google Chrome

**Prerequisites**

- Java JDK
- Eclipse IDE
- Google Chrome
- Selenium WebDriver dependencies
- TestNG

**Application URL**

https://proton.me/mail

**How to Run the Tests**

1. Open the project in Eclipse.
2. Verify that Java, Selenium, and TestNG are configured.
3. Right-click `testng.xml`.
4. Select **Run As → TestNG Suite**.
5. Wait for execution to finish.
6. Review the test results.

**Test Coverage**

- Proton Mail navigation
- Login page and empty-password validation
- Login and sign-out
- Compose and send email
- Draft auto-save and editing
- Delete and send drafts
- Mark email as unread
- Star email
- Delete and restore emails
- Bulk delete
- Email search
- Create and delete filters
- Attachment functionality
- Undo Send functionality

**Test Reports**

TestNG reports are generated after test execution, if configured. Check the `test-output` folder for reports such as `index.html` and `emailable-report.html`.

The report contains passed, failed, and skipped test results.

**Defect Reporting**

No defects were identified during the scenarios executed. This statement applies only to the scenarios tested.

**Test Design**

Selenium WebDriver is used to automate browser actions, and TestNG is used to execute test cases and review results. Page Object Model is used where implemented to separate page actions from test scenarios.

**Limitations**

Test execution depends on internet connectivity, application changes, account data, and the scenarios implemented in the project.

**Conclusion**

This project helped me practise Java, Selenium WebDriver, TestNG, and functional testing by automating important Proton Mail features.