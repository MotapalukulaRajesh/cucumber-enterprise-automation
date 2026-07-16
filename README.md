# Cucumber Enterprise Automation Framework

A robust, scalable test automation framework built with **Selenium**, **Cucumber**, and **TestNG** for testing OrangeHRM and other web applications.

## 📋 Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Setup Instructions](#setup-instructions)
- [Running Tests](#running-tests)
- [Current CLI Support](#current-cli-support)
- [Configuration](#configuration)
- [Writing Test Cases](#writing-test-cases)
- [Reports](#reports)
- [Troubleshooting](#troubleshooting)

---

## ✨ Features

✅ **Page Object Model (POM)** - Clean separation of UI elements and test logic  
✅ **Behavior-Driven Development (BDD)** - Cucumber feature files for non-technical stakeholders  
✅ **Cross-Browser Testing** - Support for Chrome, Firefox, and Edge  
✅ **Multi-Environment Support** - QA, UAT, and PROD configurations  
✅ **Advanced Waits** - Explicit and implicit waits with WebDriverWait  
✅ **Extent Reports** - Comprehensive HTML test reports with screenshots  
✅ **Parallel-Ready Design** - Framework structure is prepared for parallel execution once Maven plugin config is added  
✅ **Logging** - Log4j2 integration for detailed execution logs  
✅ **Screenshot Capture** - Automatic screenshots on test failure  
✅ **Object Repository** - Centralized element locator management  
✅ **WebDriverManager** - Automatic driver management without manual downloads  

---

## 🛠 Prerequisites

Before you begin, ensure you have the following installed:

- **Java** 21 or higher
- **Maven** 3.8+
- **Git**
- **IDE** (IntelliJ IDEA or Eclipse)

### Required Plugins (Optional but recommended):

- Cucumber for Java
- Gherkin

---

## 📁 Project Structure

```
cucumber-enterprise-automation/
├── src/
│   ├── main/
│   │   ├── java/com/framework/
│   │   │   ├── api/                 # API testing utilities
│   │   │   ├── config/              # Configuration management
│   │   │   ├── constants/           # Framework constants
│   │   │   ├── driver/              # Driver utilities
│   │   │   ├── elements/            # Custom WebElement utilities
│   │   │   ├── enums/               # Enumerations
│   │   │   ├── exceptions/          # Custom exceptions
│   │   │   ├── factory/             # Factory patterns (BrowserFactory, DriverFactory)
│   │   │   ├── listeners/           # TestNG listeners
│   │   │   ├── managers/            # Various managers
│   │   │   ├── pages/               # Page Object classes
│   │   │   ├── reports/             # Reporting utilities
│   │   │   ├── utils/               # Utility classes
│   │   │   └── validators/          # Validation utilities
│   │   └── resources/
│   │       ├── log4j2.xml           # Log4j2 configuration
│   │       └── objectrepository/    # Element locators (properties files)
│   │
│   └── test/
│       ├── java/com/framework/
│       │   ├── dataproviders/       # Test data providers
│       │   ├── hooks/               # Cucumber hooks
│       │   ├── runners/             # Test runner classes
│       │   └── stepdefinitions/     # Step definition classes
│       │
│       └── resources/
│           ├── config/              # Environment configs
│           │   ├── qa.properties
│           │   ├── uat.properties
│           │   └── prod.properties
│           ├── features/            # Feature files
│           │   └── Login.feature
│           └── testdata/            # Test data files
│
├── reports/                         # Test reports (generated)
├── screenshots/                     # Failure screenshots (generated)
├── logs/                           # Execution logs (generated)
├── target/                         # Maven build output
├── pom.xml                         # Maven configuration
└── README.md                       # This file
```

---

## 🚀 Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/cucumber-enterprise-automation.git
cd cucumber-enterprise-automation
```

### 2. Install Dependencies

```bash
mvn clean install
```

This command will download all required dependencies defined in `pom.xml`.

### 3. Verify Installation

```bash
mvn -v
java -version
```

### 4. Configure IDE

**For IntelliJ IDEA:**
1. Open the project: File → Open → Select project folder
2. Configure Project SDK: File → Project Structure → Project → Set SDK to Java 21
3. Enable Annotation Processing: File → Settings → Build, Execution, Deployment → Compiler → Annotation Processors → Enable Annotation Processing

---

## ▶️ Running Tests

### Run All Tests

```bash
mvn clean test
```

### Run Tests in Specific Environment

```bash
# QA Environment
mvn clean test -Denv=qa

# UAT Environment
mvn clean test -Denv=uat

# PROD Environment
mvn clean test -Denv=prod
```

### Run Tests in Specific Browser

```bash
mvn clean test -Dbrowser=chrome
# Options: chrome, firefox, edge
```

### Current Runner Behavior

- The current Cucumber runner executes features from `src/test/resources/features`.
- The current Cucumber runner uses tag filter `@Regression` (hardcoded in runner).
- Command-line overrides are currently implemented for:
  - `-Denv` (example: `qa`, `uat`, `prod`)
  - `-Dbrowser` (example: `chrome`, `firefox`, `edge`)

---

## ✅ Current CLI Support

| Property | Status | Notes |
|----------|--------|-------|
| `-Denv` | Supported | Selects configuration from `src/test/resources/config/*.properties` |
| `-Dbrowser` | Supported | Overrides browser at runtime |
| `-Dheadless` | Not implemented | `headless` exists in config files, but no runtime wiring in browser factory |
| `-Dtags` | Not implemented | Tag is currently hardcoded to `@Regression` in runner |
| `-Dfeature` | Not implemented | Runner points to full features path |
| `-Dparallel` | Not implemented | No Maven test plugin configuration for parallel execution |

### Planned/Recommended Enhancements

To support advanced CLI filtering, implement dynamic runner configuration for tags/features and add Maven Surefire/Failsafe parallel settings in `pom.xml`.

---

## ⚙️ Configuration

### Environment Configuration Files

Located in: `src/test/resources/config/`

#### qa.properties
```properties
browser=chrome
url=https://opensource-demo.orangehrmlive.com/web/index.php/auth/login
timeout=20
headless=false
```

> Note: `headless` is available in environment properties, but command-line override for headless is not yet wired in the current code.

#### uat.properties
```properties
browser=chrome
url=https://uat-demo.orangehrmlive.com/web/index.php/auth/login
timeout=20
headless=false
```

#### prod.properties
```properties
browser=chrome
url=https://prod-demo.orangehrmlive.com/web/index.php/auth/login
timeout=20
headless=false
```

### Framework Constants

Located in: `src/main/java/com/framework/constants/FrameworkConstants.java`

```java
EXPLICIT_WAIT = 20 seconds
IMPLICIT_WAIT = 10 seconds
REPORT_PATH = reports/ExtentReport.html
SCREENSHOT_PATH = screenshots/
```

---

## ✍️ Writing Test Cases

### 1. Create a Feature File

Create `src/test/resources/features/YourFeature.feature`:

```gherkin
Feature: Your feature description

  @Smoke
  Scenario: Your scenario name
    Given User launches application
    When User enters username "Admin"
    And User enters password "admin123"
    And User clicks login button
    Then User should navigate to dashboard
```

### 2. Create Page Object Class

Create `src/main/java/com/framework/pages/YourPage.java`:

```java
package com.framework.pages;

public class YourPage extends BasePage {

    private static final String PAGE = "your_page";

    public YourPage enterData(String data) {
        type(PAGE, "dataField", data);
        return this;
    }

    public String getResult() {
        return getText(PAGE, "resultLabel");
    }
}
```

### 3. Create Step Definition Class

Create `src/test/java/com/framework/stepdefinitions/YourSteps.java`:

```java
package com.framework.stepdefinitions;

import com.framework.pages.YourPage;
import io.cucumber.java.en.*;

public class YourSteps {

    private final YourPage yourPage = new YourPage();

    @Given("User is on your page")
    public void navigateToPage() {
        // Implementation
    }

    @When("User performs action with {string}")
    public void performAction(String data) {
        yourPage.enterData(data);
    }

    @Then("User should see {string}")
    public void verifyResult(String expectedResult) {
        // Assertions
    }
}
```

### 4. Add Object Repository

Add to `src/main/resources/objectrepository/your_page.properties`:

```properties
dataField=id=dataInput
resultLabel=xpath=//div[@class='result']
```

**Locator Format:** `locatorType=locatorValue`

Supported types:
- `id`
- `name`
- `xpath`
- `css`
- `classname`
- `tagname`
- `linktext`
- `partiallinktext`

---

## 📊 Reports

### Extent Report

After test execution, open:

```
reports/ExtentReport.html
```

Features:
- Test execution overview
- Pass/Fail statistics
- Execution timeline
- Screenshots of failures
- System information

### Allure Report (Optional)

Generate a static Allure report:

```bash
mvn io.qameta.allure:allure-maven:report
```

Serve Allure report locally:

```bash
mvn io.qameta.allure:allure-maven:serve
```

> If these commands are not available in your Maven setup, add the Allure Maven plugin under `build -> plugins` in `pom.xml`.

### Cucumber HTML Report

```
target/cucumber-report.html
```

---

## 🔧 Troubleshooting

### Issue: Tests fail with "WebDriver not found"

**Solution:**
- Ensure Java is installed and JAVA_HOME is set
- Run `mvn clean install` to download WebDriver
- Clear Maven cache: `mvn clean` then `mvn install`

### Issue: Element not found exceptions

**Solution:**
- Verify element locators in object repository files
- Check if element is loaded before interacting
- Increase explicit wait time in FrameworkConstants
- Use browser developer tools to inspect elements

### Issue: Tests timeout

**Solution:**
- Increase timeout values in configuration
- Check application performance
- Verify network connectivity
- Check browser performance issues

### Issue: Report not generating

**Solution:**
- Ensure `reports/` directory has write permissions
- Check ExtentReportManager initialization
- Verify log4j2.xml configuration
- Check logs for errors

### Issue: Screenshot not captured

**Solution:**
- Verify `screenshots/` directory exists
- Check directory permissions
- Ensure test fails (screenshots only captured on failure)
- Review ScreenshotUtils implementation

---

## 📚 Best Practices

1. **Use Page Object Model** - Keep UI logic separated from test logic
2. **Meaningful Scenario Names** - Describe what is being tested
3. **DRY Principle** - Reuse common steps and methods
4. **Explicit Waits** - Prefer explicit waits over implicit waits
5. **Test Data** - Use external data sources (Excel, JSON, Database)
6. **Logging** - Log important steps for debugging
7. **Tags** - Use @Smoke, @Regression, @Critical for test categorization
8. **Error Handling** - Use try-catch wisely and log exceptions
9. **Cleanup** - Ensure proper teardown in @After hooks
10. **Documentation** - Document complex step definitions

---

## 🔒 Security Best Practices

- Never commit credentials in code
- Use environment variables for sensitive data
- Encrypt sensitive test data
- Use secure connection (HTTPS) for URLs
- Review logs before sharing
- Sanitize reports before public sharing

---

## 📞 Support & Contribution

For issues, questions, or contributions:

1. Check existing documentation
2. Review error logs
3. Search existing issues
4. Create a new issue with details
5. Submit pull requests for enhancements

---

## 📄 License

This project is licensed under the MIT License.

---

## 🙏 Acknowledgments

- Selenium WebDriver
- Cucumber
- TestNG
- Extent Reports
- WebDriverManager

---

**Last Updated:** July 16, 2026  
**Framework Version:** 1.0-SNAPSHOT  
**Java Version:** 21

