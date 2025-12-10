# Selenium POM Framework

A complete Selenium Page Object Model (POM) framework structure for test automation.

## Project Structure

```
test_antigravity/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/test/org/
│   │           ├── pages/          # Page Object classes
│   │           │   ├── BasePage.java
│   │           │   ├── LoginPage.java
│   │           │   └── HomePage.java
│   │           └── utils/          # Utility classes
│   │               ├── WebDriverFactory.java
│   │               ├── WaitHelper.java
│   │               ├── ConfigReader.java
│   │               └── ScreenshotUtil.java
│   └── test/
│       ├── java/
│       │   └── com/test/org/
│       │       ├── base/           # Base test class
│       │       │   └── BaseTest.java
│       │       └── tests/          # Test classes
│       │           └── LoginTest.java
│       └── resources/
│           └── config/             # Configuration files
│               ├── config.properties
│               └── testng.xml
└── pom.xml
```

## Framework Components

### Base Classes
- **BasePage**: Contains common page operations and WebDriver initialization
- **BaseTest**: Handles WebDriver setup and teardown with TestNG annotations

### Page Objects
- **LoginPage**: Page object for login functionality
- **HomePage**: Page object for home page interactions

### Utilities
- **WebDriverFactory**: Manages browser instances (Chrome, Firefox, Edge) using WebDriverManager
- **WaitHelper**: Provides reusable explicit wait methods
- **ConfigReader**: Reads configuration from properties file
- **ScreenshotUtil**: Captures and saves screenshots

### Configuration
- **config.properties**: Contains application URL, browser type, timeouts, and test data
- **testng.xml**: TestNG suite configuration

## Running Tests

### Using Maven
```bash
# Run all tests
mvn clean test

# Run specific test suite
mvn clean test -DsuiteXmlFile=src/test/resources/config/testng.xml
```

### Using TestNG XML
```bash
mvn test -Dtestng.xml=src/test/resources/config/testng.xml
```

## Configuration

Edit `src/test/resources/config/config.properties` to configure:
- Base URL
- Browser type (chrome, firefox, edge)
- Timeouts
- Test credentials

## Adding New Tests

1. Create a new Page Object class in `src/main/java/com/test/org/pages/`
2. Extend `BasePage` and use `@FindBy` annotations
3. Create a test class in `src/test/java/com/test/org/tests/`
4. Extend `BaseTest` and use TestNG annotations
5. Add the test class to `testng.xml`
