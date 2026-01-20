# MakeMyTrip Automation Framework

## Industry-Standard Selenium Automation Framework

A complete, production-ready Selenium automation framework built using Java 8, TestNG, Maven, and Page Object Model (POM) design pattern.

---

## 📁 Project Structure

```
mmt-automation-framework/
├── pom.xml                              # Maven configuration
├── testng.xml                           # TestNG suite configuration
├── README.md                            # This file
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/cts/mmt/
│   │   │       ├── base/
│   │   │       │   └── BaseTest.java        # Base test class
│   │   │       ├── pages/
│   │   │       │   ├── HomePage.java        # Home page object
│   │   │       │   ├── CabsPage.java        # Cabs booking page
│   │   │       │   ├── GiftCardsPage.java   # Gift cards page
│   │   │       │   ├── HotelsPage.java      # Hotels booking page
│   │   │       │   └── LoginPage.java       # Login page object
│   │   │       └── utils/
│   │   │           ├── ConfigReader.java    # Configuration utility
│   │   │           ├── ExtentManager.java   # Extent Reports manager
│   │   │           └── ScreenshotUtil.java  # Screenshot utility
│   │   └── resources/
│   │       └── logging.properties           # Java logging config
│   │
│   └── test/
│       ├── java/
│       │   └── com/cts/mmt/tests/
│       │       ├── CabBookingTest.java      # Cab booking tests
│       │       ├── GiftCardTest.java        # Gift card tests
│       │       ├── HotelPageTest.java       # Hotel page tests
│       │       └── LoginTest.java           # Login tests
│       └── resources/
│           ├── config.properties            # Test configuration
│           └── log4j2.xml                   # Log4j2 configuration
│
└── test-output/
    ├── reports/                             # Extent HTML reports
    ├── screenshots/                         # Failure screenshots
    └── logs/                                # Log files
```

---

## 🛠️ Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 8 | Programming Language |
| Selenium WebDriver | 4.15.0 | Browser Automation |
| TestNG | 7.8.0 | Testing Framework |
| Maven | 3.x | Build Tool |
| WebDriverManager | 5.6.2 | Driver Management |
| Extent Reports | 5.1.1 | HTML Reporting |
| Log4j2 | 2.22.0 | Logging Framework |

---

## 📋 Problem Statements Covered

### 1. Book One Way Outstation Cab
- **From:** Delhi
- **To:** Manali, Himachal Pradesh
- **Car Type:** SUV
- **Output:** Display lowest cab charges

### 2. Gift Card Invalid Email Validation
- Navigate to Gift Cards
- Fill card details with invalid email
- Capture and display error message
- Take screenshot

### 3. Hotel Booking - Adult Numbers
- Navigate to Hotels page
- Extract adult person dropdown values
- Store in a List
- Display the numbers

---

## 🚀 How to Run

### Prerequisites
1. Java 8 JDK installed
2. Maven installed
3. Chrome browser installed

### Run All Tests
```bash
mvn clean test
```

### Run Specific Test Class
```bash
mvn test -Dtest=CabBookingTest
mvn test -Dtest=GiftCardTest
mvn test -Dtest=HotelPageTest
```

### Run via TestNG XML
```bash
mvn test -DsuiteXmlFile=testng.xml
```

---

## 📊 Reports

### Extent Reports
- Location: `test-output/reports/TestReport_<timestamp>.html`
- Features:
  - Test status (PASS/FAIL/SKIP)
  - Screenshots on failure
  - Step-by-step logs
  - Execution time
  - System information

### Screenshots
- Location: `test-output/screenshots/`
- Captured automatically on test failure

### Logs
- Location: `test-output/logs/`
- Levels: INFO, DEBUG, ERROR

---

## 🏗️ Framework Architecture

### Page Object Model (POM)
- Each page has its own class
- Uses `@FindBy` annotations (PageFactory)
- Encapsulates page-specific actions
- Returns page objects for fluent navigation

### BaseTest Class
- WebDriver setup/teardown
- Extent Reports integration
- Screenshot capture on failure
- Logging utilities

### Key Design Patterns
1. **Singleton Pattern** - ConfigReader, ExtentManager
2. **Page Object Model** - All page classes
3. **Factory Pattern** - WebDriver initialization
4. **ThreadLocal** - Parallel execution support

---

## ⚙️ Configuration

### config.properties
```properties
browser=chrome
url=https://www.makemytrip.com/
implicit.wait=10
explicit.wait=20
```

### testng.xml
- Suite-level configuration
- Test grouping
- Parallel execution settings

---

## 📝 Test Classes

| Test Class | Test Cases | Description |
|------------|------------|-------------|
| CabBookingTest | 3 | Outstation cab booking Delhi → Manali |
| GiftCardTest | 3 | Gift card form with invalid email |
| HotelPageTest | 4 | Extract adult numbers from dropdown |
| LoginTest | 4 | Login framework demonstration |

---

## 🎯 Key Automation Scope

- ✅ Handling popups
- ✅ Form filling
- ✅ Error message capture
- ✅ Page scrolling
- ✅ Dropdown extraction
- ✅ Menu navigation
- ✅ Cross-page navigation
- ✅ Screenshot capture

---

## 👨‍💻 Author

**CTS Automation Team**

---

## 📄 License

This project is for educational and demonstration purposes.
