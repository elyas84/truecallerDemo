TrueCallerDemo

This framework is capable of handling both Android and iOS mobile applications with a single unified codebase.
It’s built using Appium, Java, and Maven, designed for scalability, maintainability, and cross-platform testing.

```
📂 Repository Structure

truecallerDemo/
├── .idea/  
├── logs/  
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── base/
│   │   │   ├── driver/
│   │   │   ├── extentReport/
│   │   │   ├── listeners/
│   │   │   └── pageObject/
│   │   └── resources/
│   │       ├── extentReport.properties
│   │       └── log4j.properties
│   ├── test/
│   │   └── java/
│   │       └── tests/
│   └── test-suites/
│       └── smoke.xml
├── app.properties  
├── ios.properties  
├── samsungGalaxyS20.properties  
├── pom.xml  
├── .gitignore  
└── README.md

```

```
📘 Description of Folders and Files
Folder / File

Description

.idea/
IDE configuration files for IntelliJ / Android Studio

logs/
Stores execution logs, screenshots, and generated reports

src/main/java/base/
Core framework components such as ActionHelper, AppData, ConfigReader, and TestBase

src/main/java/driver/
Driver management classes (AppDriver, AppFactory, AppiumServer)

src/main/java/extentReport/
Configuration and setup for Extent Reports

src/main/java/listeners/
TestNG listeners for handling test lifecycle events (e.g., logging, reporting)

src/main/java/pageObject/
Page Object Model classes representing app screens, locators, and reusable actions

src/main/resources/
Configuration files (extentReport.properties, log4j.properties)

src/test/java/tests/
Test classes — isolated from main framework code

src/test-suites/
Contains TestNG XML suite files (e.g., smoke.xml) used for Maven execution

app.properties

Android app capabilities and configuration

ios.properties

iOS app capabilities and configuration

samsungGalaxyS20.properties

Example device-specific configuration

pom.xml

Maven configuration and dependencies

.gitignore

Specifies ignored files for version control

README.md

Project documentation (this file)

```

```
⚙️ Features & Highlights

	1.	Cross-Platform Automation — Single codebase for Android and iOS
	2.	Singleton Driver Management — Ensures only one Appium driver instance during execution
	3.	Dynamic Platform Handling — AppFactory launches the correct platform at runtime
	4.	Built-in Reporting — Extent Reports integrated for detailed test execution insights
	5.	Centralized Configuration — All environment variables, capabilities, and logs managed via .properties files
	6.	Scalable Test Design — Clear separation between core framework, page objects, and test execution layers
```

```
🧩 Core Components Overview

🧱 Base Folder
	•	ActionHelper — Acts as a utility layer containing common reusable methods (clicks, waits, input, etc.)
	•	AppData — Stores system variables that can be manually configured or overridden via VM options
	•	ConfigReader — Reads and loads data from .properties files
	•	TestBase — Handles setup and teardown activities before and after each test

🚀 Driver Folder
	•	AppDriver — Singleton class managing the Appium driver instance
	•	AppFactory — Responsible for launching the correct platform (Android or iOS) based on configuration
	•	AppiumServer — Starts and manages the Appium server programmatically

📊 ExtentReport Folder
	•	Contains configuration classes for initializing and customizing Extent Reports

🎧 Listeners Folder
	•	Contains listener classes that handle TestNG events (e.g., logging results, taking screenshots on failure)

📱 PageObject Folder
	•	Contains one class per app screen, each with unique locators and methods following the Page Object Model pattern

🧪 Test Folder
	•	Contains actual test scripts (in src/test/java/tests)
	•	Fully isolated from the core framework for clean separation

🧾 Test-Suites Folder
	•	Contains XML suite files (e.g., smoke.xml) defining which tests to execute and their configurations
```

```
🧪 Setup & Usage

1. Prerequisites
	•	Java 8+
	•	Maven
	•	Android SDK / Xcode (depending on platform)
	•	Appium Server (installed and running)
	•	Real device or emulator/simulator
	
2. Configuration
	•	Edit app.properties for Android app capabilities (app path, package, activity, etc.)
	•	Edit ios.properties for iOS capabilities (bundle ID, automation name, etc.)
	•	Modify or create device-specific configuration files (e.g., samsungGalaxyS20.properties)
```

```
# Run tests on Android
mvn clean test -Dplatform=android -Dconfig=app.properties -DsuiteXmlFile=smoke.xml

# Run tests on iOS
mvn clean test -Dplatform=ios -Dconfig=ios.properties -DsuiteXmlFile=smoke.xml
```

```
View Results
	•	Logs, screenshots, and Extent Reports are generated under the target/html-reports/
	•	Reports can also be opened in a browser for visual analysis
```

```
🧠 Conceptual Overview

	•	The AppDriver and AppFactory dynamically initialize the correct platform driver (Android/iOS).
	•	Page Objects represent app screens and encapsulate user interactions.
	•	Test Classes call methods from the Page Objects, keeping test logic clean and readable.
	•	Listeners capture and log test events, while Extent Reports provide visual feedback.
	•	Properties files manage configurations, ensuring tests are portable and easy to maintain.
```


