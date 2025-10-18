# truecallerDemo

This framework is capable of handling both Android and iOS mobile applications with one unified codebase.

📂 Repository Structure

```
truecallerDemo/
├── .idea/  
├── logs/  
├── src/
│   ├── android/  
│   │   ├── pages/  
│   │   ├── tests/  
│   │   └── utils/  
│   └── ios/  
│       ├── pages/  
│       ├── tests/  
│       └── utils/  
├── app.properties  
├── ios.properties  
├── samsungGalaxyS20.properties  
├── pom.xml  
├── .gitignore  
└── README.md
```

```
Description of folders and files:
•	.idea/ — IDE configuration (for IntelliJ / Android Studio)
•	logs/ — test execution logs, screenshots, reports, etc.
•	src/ — main test source code, split by platform
•	android/ — Android-specific pages, tests and helpers
•	ios/ — iOS-specific pages, tests and helpers
•	app.properties — common or Android app config / capabilities
•	ios.properties — iOS config / capabilities
•	samsungGalaxyS20.properties — device-specific config (e.g. for one Android device)
•	pom.xml — Maven project file, dependencies & build scripts
•	.gitignore — files and directories to ignore
•	README.md — this file
```

```
⚙️ Features & Highlights
•	Cross-platform support: Write tests once and run them on Android and iOS devices/emulators without duplicating logic.
•	Device / platform configurations are externalized (via .properties files) to enable flexibility.
•	Clean separation: platform-specific code resides under src/android or src/ios.
•	Logging, screenshots, and reports captured under logs/ for easy debugging.
```

```
🧪 Setup & Usage

1. Prerequisites
   • Java 8+ (or your required version)
   • Maven
   • Android SDK / Xcode + iOS tooling
   • Real devices or emulators / simulators
   •    (If using Appium) Appium server installed & running
2. Configure devices / apps
   • Edit app.properties for Android capabilities (app path, package, activity, etc.)
   • Edit ios.properties for iOS capabilities
   • Optionally, create device-specific .properties (e.g. samsungGalaxyS20.properties)

```

```
Run tests
mvn clean test -Dplatform=andorid -Denv=release -Dconfig=app.properties -DsuiteXmlFile=smoke.xml
mvn clean test -Dplatform=ios -Denv=release -Dconfig=ios.properties -DsuiteXmlFile=smoke.xml
```

```
4. View results
   • Logs, screenshots, and reports are under the logs/ folder
   • Review test output in the console or via report viewer

⸻

🧱 How It Works (Conceptual)
• A BaseTest / BaseDriver class initializes the Appium (or driver) session according to the selected platform, reading
the relevant .properties.
• Shared test logic / helper methods call into platform-specific implementations when necessary (via abstract classes,
interfaces, or conditional logic).
• Page object models are duplicated per platform under src/android/pages and src/ios/pages (if UI differs), but your
test flow can remain largely the same.
• Device properties, timeouts, paths, and capabilities are externalized to properties files, so tests are easily
customizable for different devices or OS versions.

```
