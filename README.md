# 24CS2021 Mobile Application Development Practice Lab Collection

A complete, beginner-friendly collection of 12 practice lab applications matching the `24CS2021 Mobile Application Development` examination paper.

Each module inside `labs/` represents a self-contained, independent practical exam solution written in clean, simple Kotlin and Jetpack Compose.

---

## 1. Project Architecture

This repository uses a multi-module Gradle structure where `:app` acts as the single runnable application host, and each module under `labs/` is an independent Android library (`com.android.library`).

```text
MyApplication/
├── app/                          # Main runnable Android host application
│   └── src/main/java/.../
│       └── MainActivity.kt       # Practice Question Switcher
├── labs/
│   ├── food-ordering/            # Question 1: Food Ordering (Explicit Intent & Extras)
│   ├── announcements/            # Question 2: Announcement Feed (Dynamic UI)
│   ├── product-store/            # Question 3 & 6: Product Store (Navigation Compose)
│   ├── contact-directory/        # Question 4: Contact Directory (LazyColumn & Snackbar)
│   ├── background-sync/          # Question 5: Background Sync (WorkManager Tasks)
│   ├── reading-list/             # Question 7: Reading List (Room DB & DataStore Sort)
│   ├── comments-api/             # Question 8: Comments List (REST API & Retrofit)
│   ├── feedback-collector/       # Question 9: Feedback Collector (Real-Time List)
│   ├── tip-calculator/           # Question 10: Tip Calculator (Logic & Unit Tests)
│   ├── shopping-cart/            # Question 11: Shopping Cart (Quantities & Discounts)
│   ├── firebase-auth/            # Question 12: User Authentication (Login & Home)
│   └── event-registration/       # Question 13: Event Registration (Forms & List)
├── gradle/
│   └── libs.versions.toml
├── settings.gradle.kts
├── build.gradle.kts
└── README.md
```

---

## 2. Practice Exam Questions Included

### Question 1: Food Ordering App (`:labs:food-ordering`)
- **Concepts**: Explicit Intents, Data passing between Activities, Order summary screen.
- **Implementation**: Food menu checkboxes, total calculation, launching `OrderSummaryActivity` via explicit `Intent` with item and price extras.

### Question 2: Announcement Feed App (`:labs:announcements`)
- **Concepts**: Dynamic UI generation from data source, `LazyColumn`.
- **Implementation**: Dynamic list handling Plain Text Notices, Urgent Alerts (`[URGENT]`), and Action Items with interactive acknowledgment buttons.

### Question 3 & 6: Product Store App (`:labs:product-store`)
- **Concepts**: Navigation Compose, Screen Arguments, Back Navigation.
- **Implementation**: Product Catalog screen (name, price) navigating to Product Detail screen passing `productId` argument.

### Question 4: Contact Directory App (`:labs:contact-directory`)
- **Concepts**: `LazyColumn`, Reusable Composables, `Snackbar`.
- **Implementation**: Scrollable list of employees (name, designation, phone) displaying selected employee name in a Snackbar on click.

### Question 5: Background Sync App (`:labs:background-sync`)
- **Concepts**: WorkManager, Periodic Tasks, Work Status.
- **Implementation**: "Start Sync" and "Cancel Sync" buttons running a background `CoroutineWorker` and observing work state (`ENQUEUED`, `RUNNING`, `SUCCEEDED`, `CANCELLED`).

### Question 7: Reading List App (`:labs:reading-list`)
- **Concepts**: Room Database, DataStore Preferences, Flow.
- **Implementation**: Book creation, Room persistent storage, delete action, and sorting toggle ("Title A-Z" vs "Newest First") persisted across app restarts via DataStore.

### Question 8: Comments List App (`:labs:comments-api`)
- **Concepts**: Retrofit, REST API, JSON Parsing, Loading State.
- **Implementation**: Requests `https://jsonplaceholder.typicode.com/comments` using Retrofit, rendering progress indicator and list of names and emails.

### Question 9: Feedback Collector App (`:labs:feedback-collector`)
- **Concepts**: State Management, Form Inputs, Real-Time List.
- **Implementation**: Student name field, feedback comment field, Submit button, and real-time updating list displaying timestamps.

### Question 10: Tip Calculator App (`:labs:tip-calculator`)
- **Concepts**: Logic Separation, Unit Testing (JUnit).
- **Implementation**: Bill amount input, 10%/15%/20% tip buttons, calculation logic separated in `TipCalculator.kt` with passing JUnit tests.

### Question 11: Shopping Cart App (`:labs:shopping-cart`)
- **Concepts**: Quantity Selectors, Discount Codes, Logic Testing.
- **Implementation**: Product list with `+` and `-` quantity controls (never below 0), subtotal calculation, discount code `SAVE10` (10% off), with unit tests in `CartCalculatorTest.kt`.

### Question 12: User Authentication App (`:labs:firebase-auth`)
- **Concepts**: Auth Flow, Protected Screens, Access Restriction.
- **Implementation**: Login/Register screen with email & password fields, redirecting authenticated users to protected Home screen with Logout functionality.

### Question 13: Event Registration App (`:labs:event-registration`)
- **Concepts**: Dropdowns, Real-Time Records, Count Summaries.
- **Implementation**: Name, email, event dropdown (Hackathon, Workshop, Quiz), real-time list, and total registration counter at top.

---

## 3. Running a Specific Question in `MainActivity`

In `MainActivity.kt`:

1. **Runtime Selector**: Select any Question 1–13 from the top dropdown menu.
2. **Direct Submission**: Uncomment your target question's root composable (e.g. `FoodOrderingApp()`, `TipCalculatorApp()`, `ReadingListApp()`) directly inside `setContent`.

---

## 4. Running Unit Tests

Run unit tests for logic-based questions (Questions 10 & 11):
- **Tip Calculator Test**: `.\gradlew.bat :labs:tip-calculator:test`
- **Shopping Cart Test**: `.\gradlew.bat :labs:shopping-cart:test`
