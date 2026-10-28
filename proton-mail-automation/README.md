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

**Automation Strategy and Design Decisions**

**1. Which tests did you choose NOT to automate, and why?**

I would not automate every test. Tests that need human observation, such as checking whether the page looks correct, can be tested manually. I would also avoid automating features that are not stable yet. I would first automate important and frequently used features.

**2. Which 5 tests would you run on every pull request? Which would you run nightly? Why?**

For every pull request, I would run these five tests:

1. Open the Proton Mail website.
2. Check login validation with an empty password.
3. Log in and open the Inbox.
4. Compose and send an email.
5. Search for an email using a keyword.

These tests check the main features and give quick feedback.

I would run all regression tests at night, including drafts, filters, attachments, and email actions. This takes more time but helps check more features.

**3. How would you check a test that passes locally but fails sometimes in CI?**

I would check the error message, screenshots, and logs to find the problem. I would check whether the issue is caused by slow loading, incorrect locators, network problems, or different browser versions. Then I would fix the actual problem and run the test again.

**4. How would you reduce test failures when the UI changes frequently?**

I would keep locators in Page Object classes so they are easier to update. I would use stable locators and explicit waits instead of fixed sleep statements. I would also keep test cases independent and update the locators when the application UI changes.

**5. How would you increase the test suite from 20 tests to 200 or more?**

I would organize tests by feature, such as login, inbox, drafts, search, and attachments. I would reuse page methods, keep test data separate, and use TestNG groups to organize smoke and regression tests. I would also check test reports regularly and fix flaky tests to make the suite easier to maintain.
