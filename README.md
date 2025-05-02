# Taskly Teaching App (TDD)

Taskly is a simple Kotlin/Jetpack Compose sample application designed as a baseline for **Test-Driven Development** (TDD).  


## Table of Contents

- [Prerequisites](#prerequisites)  
- [Setup Environment](#setup-environment)  
- [Getting Started](#getting-started)  
- [Running Tests](#running-tests)  
- [Getting Started with Appium](#getting-started-with-appium)  

---

## Prerequisites

- **JDK 11+**  
- **Android SDK**  
- **Android Studio** (recommended) or command-line Gradle  

---

## Setup Environment

We’ve provided a helper script to configure your Android SDK paths:

```bash
# from the mimeo dl root
cd install_scripts
./install_env.sh
```
## Getting Started

git clone https://github.com/Edrzapi/Taskly_Android.git
cd Taskly_Android

# Make the Gradle wrapper executable (if needed)
```bash
chmod +x gradlew
```
# Build & install onto your running emulator/device:
```bash
./gradlew installDebug
```
# Or open in Android Studio and hit ▶️ Run

## Running Tests

Ensure the basic tests work:
```bash
./gradlew testDebugUnitTest
```

## Getting Started with Appium

Ensure Appium is installed globablly:
```bash
npm install -g appium@latest

```
Get the uiauto driver, we use the default version in our examples.

```bash
appium driver install uiautomator2
```

Finally, run appium before running your testcase.

```bash
appium
```

Your journey begins in the introduction-task branch. Your trainer will guide you through each step of the walkthrough.

This baseline project provides a fully working app to start with. As you progress through each stage, you'll gradually refactor and enhance the application using Test-Driven Development (TDD) practices. Each step will challenge you to improve the codebase until your version mirrors—or even improves on—the provided functionality.

Focus on writing tests first, building incrementally, and understanding the why, not just the how.
