# 🛒 QKART E-Commerce Automation Framework (TestNG)

🎥 **Demo Video**  
[![Demo](https://img.youtube.com/vi/wGlLS7ni_Vo/0.jpg)](https://youtu.be/wGlLS7ni_Vo)

---

## 📌 Project Overview
This project implements a **scalable end-to-end UI automation framework** for the **QKART e-commerce application** using **Selenium WebDriver, Java, and TestNG**.  
The framework validates **critical business workflows** such as user onboarding, product discovery, cart management, checkout, and error handling using **data-driven and grouped test execution**.

---

## 🧱 Framework Architecture & Design
- Implemented **Page Object Model (POM)** for clean separation of concerns.
- Created dedicated page classes:
  - `Register`
  - `Login`
  - `Home`
  - `Checkout`
  - `SearchResult`
- Centralized test execution in `QKART_Tests`.
- Designed for **maintainability, reusability, and scalability**.

---

## 🔄 Data-Driven Testing
- Implemented **TestNG DataProvider** with method-level switching logic.
- Supplied multiple datasets dynamically based on test case name.
- Covered variations for:
  - User credentials
  - Product names
  - Address inputs
  - Cart quantities
  - Contact Us form data
- Reduced code duplication while improving coverage.

---

## 🧪 Test Coverage
Automated **11+ real-world test scenarios**, including:

- New user registration
- Duplicate user registration validation
- Login and logout functionality
- Product search (valid & invalid inputs)
- Add to cart and quantity update
- Checkout and order placement
- Insufficient wallet balance validation
- Cart persistence across multiple tabs
- Privacy Policy & Terms of Service validation
- Advertisement iframe interaction validation
- Contact Us form submission

---

## ⏳ Synchronization & Stability
- Used **Explicit Waits (WebDriverWait)** for dynamic elements.
- Applied **FluentWait** with polling for handling unstable UI behavior.
- Managed:
  - Multiple windows & tabs
  - Iframes
  - Modal dialogs
  - URL redirections
- Reduced flaky test execution.

---

## ✅ Assertion Strategy
- Used **Hard Assertions** for critical failures.
- Used **Soft Assertions** to collect multiple failures in a single test run.
- Validated:
  - UI messages & alerts
  - Page navigation & URLs
  - Cart contents & quantities
  - Modal dialogs and popups

---

## 🧪 Test Execution Management
- Organized tests using **TestNG Groups**:
  - `Sanity_test`
  - `Regression_Test`
- Controlled execution via **TestNG XML suite**.
- Used:
  - Priorities
  - Group dependencies
  - Group-based execution
- Enabled flexible execution strategies.

---

## 📸 Logging & Debugging
- Implemented **custom execution logging** with timestamps.
- Captured **screenshots**:
  - On test start
  - On failure
  - On test completion
- Improved failure investigation and traceability.

---

## 🛠️ Tools & Technologies Used
- **Language:** Java  
- **Automation Tool:** Selenium WebDriver  
- **Test Framework:** TestNG  
- **Design Pattern:** Page Object Model (POM)  
- **Execution:** Selenium WebDriver  
- **Build & Config:** TestNG XML  
- **Utilities:** DataProvider, SoftAssert, Screenshots, Actions, JavaScriptExecutor  
- **Browser:** Google Chrome  

---

## 🖼️ Tools & Frameworks Overview

<p align="left"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="Java" width="40" height="40"/> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/selenium/selenium-original.svg" alt="Selenium" width="40" height="40"/> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/chrome/chrome-original.svg" alt="Chrome" width="40" height="40"/> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/intellij/intellij-original.svg" alt="IntelliJ IDEA" width="40" height="40"/> <img src="https://avatars.githubusercontent.com/u/12528662?s=200&v=4" alt="TestNG" width="40" height="40"/> </p>

---
