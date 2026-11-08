# Exam-Ready Android Practical Lab Collection

A modular, exam-ready collection of complete Android mini-applications built with Kotlin and Jetpack Compose. Each module under `labs/` represents a real practical scenario that can be used directly or quickly adapted to match an examination question.

---

## 1. Project Architecture & Structure

This repository uses a Gradle multi-module architecture where `:app` acts as the single runnable host application and each directory under `labs/` is an independent Android library module (`com.android.library`).

```text
MyApplication/
├── app/                       # Main runnable Android application
│   └── src/main/java/.../
│       └── MainActivity.kt    # Project Switcher & Host Activity
├── labs/
│   ├── layouts/               # Basic Compose Layouts & UI elements
│   ├── food-ordering/         # Explicit Intent, ActivityExtras, Order summary
│   ├── announcements/         # Dynamic UI feed with multiple card types
│   ├── product-store/         # Jetpack Compose Navigation & Arguments
│   ├── contact-directory/     # LazyColumn & Snackbar selection
│   ├── reading-list/          # Room DB + DataStore Sorting Preference (Most Important)
│   ├── comments-api/          # Retrofit + REST API + Gson
│   ├── background-sync/       # WorkManager One-Time & Periodic Tasks
│   └── fragments/             # AndroidX FragmentContainerView & Transactions
├── gradle/
│   └── libs.versions.toml
├── settings.gradle.kts
├── build.gradle.kts
└── README.md
```

---

## 2. Practical Scenarios Included

### A. Reading List (`:labs:reading-list`) — *Primary Data & Storage Lab*
- **Scenario**: Personal reading list manager.
- **Technologies**: Room Database, Preferences DataStore, Kotlin Flow, Compose `LazyColumn`.
- **Features**:
  1. Add a book with `title` and `author`.
  2. Persistent storage using Room database (`Book` Entity & `BookDao`).
  3. Delete book item with delete button.
  4. Toggle sorting between **Title (A-Z)** and **Newest First**.
  5. Persistence of sorting selection using **DataStore Preferences**.
  6. Automatic UI updates via `Flow` when database or sorting preferences change.

### B. Food Ordering (`:labs:food-ordering`) — *Intents & Activity Extras Lab*
- **Scenario**: Food menu ordering system.
- **Technologies**: Explicit Intent, Activity Extras, Compose UI, Animations.
- **Features**: Menu with checkboxes, total price calculation, passing selected items, total price, and customer note via explicit `Intent` to `OrderSummaryActivity`, and order confirmation animation.

### C. Announcements (`:labs:announcements`) — *Dynamic UI & Data Sources Lab*
- **Scenario**: Campus announcement feed.
- **Technologies**: Dynamic UI rendering, `LazyColumn`, Reusable Card Composables.
- **Features**: Normal, Urgent (highlighted), Icon, and Action announcements with interactive callback buttons.

### D. Product Store (`:labs:product-store`) — *Navigation Compose Lab*
- **Scenario**: E-commerce product catalog.
- **Technologies**: `NavHost`, `NavController`, Composable routes, Navigation Arguments.
- **Features**: Product catalog list screen, details screen receiving `productId` argument, back navigation.

### E. Contact Directory (`:labs:contact-directory`) — *Lists & Feedback Lab*
- **Scenario**: Employee contact list.
- **Technologies**: `LazyColumn`, Reusable Rows, `SnackbarHostState`.
- **Features**: Scrollable list displaying name, designation, and phone number, with Snackbar notifications on click.

### F. Comments App (`:labs:comments-api`) — *REST API & Retrofit Lab*
- **Scenario**: Live comment viewer.
- **Technologies**: Retrofit 2, Gson Converter, Coroutines, REST API.
- **Features**: Requests `https://jsonplaceholder.typicode.com/comments`, parses JSON, displays loading indicator, error state with Retry button, empty state, and list of comments.

### G. Background Sync (`:labs:background-sync`) — *WorkManager Lab*
- **Scenario**: Background data synchronization.
- **Technologies**: WorkManager, `CoroutineWorker`.
- **Features**: Triggers One-Time sync work and Periodic sync work (respecting 15-minute minimum interval), observes task status (`WorkInfo`), and cancels unique work.

### H. Compose Layouts (`:labs:layouts`) — *UI Fundamentals Lab*
- **Scenario**: Interactive layout playground.
- **Technologies**: Compose Layouts, Modifiers, State.
- **Features**: Text, Button, TextField, Column, Row, Box, Padding, Arrangement, and Alignment.

### I. Fragments (`:labs:fragments`) — *Legacy Interop & Dynamic UI Lab*
- **Scenario**: AndroidX Fragment manager.
- **Technologies**: AndroidX `FragmentManager`, `FragmentContainerView`, `ComposeView`.
- **Features**: Adding, replacing, and removing `DemoFragment` dynamically inside Compose using `AndroidView`.

---

## 3. Switching Between Scenarios in `MainActivity`

Each lab module exposes a public root composable (e.g. `ReadingListApp()`, `FoodOrderingApp()`, `CommentsApiApp()`).

In `MainActivity.kt`, you can switch between applications in two ways:

1. **At Runtime (Interactive Dropdown)**:
   Run the app as-is and select the desired practical scenario from the top dropdown menu.

2. **Direct Submission Mode (Exam Ready)**:
   In `MainActivity.kt`, call the exact app composable directly inside `setContent`:

```kotlin
setContent {
    MyApplicationTheme {
        ReadingListApp() // Directly renders the Reading List App
    }
}
```

---

## 4. Adapting a Scenario for an Examination Question

To adapt an existing scenario to a new question (e.g., converting **Reading List** into a **Student Records** app):

1. **Entity**: Rename `Book.kt` to `Student.kt` and change fields (e.g. `name`, `rollNo`, `gpa`).
2. **DAO**: Update `BookDao.kt` SQL queries to query student records (e.g. `SELECT * FROM students ORDER BY rollNo ASC`).
3. **Database**: Update `@Database(entities = [Student::class], ...)` in `BookDatabase.kt`.
4. **UI**: Update input fields in `ReadingListScreen.kt` to accept student name and roll number.

---

## 5. Submitting a Clean Single-Project Application

If your examination requires submitting ONLY the Reading List module without extra lab modules:

1. Open `settings.gradle.kts` and keep only `:app` and `:labs:reading-list`:
   ```kts
   include(":app")
   include(":labs:reading-list")
   ```
2. Open `app/build.gradle.kts` and keep only `:labs:reading-list` in `dependencies`:
   ```kts
   implementation(project(":labs:reading-list"))
   ```
3. Open `MainActivity.kt` and render `ReadingListApp()`:
   ```kotlin
   setContent {
       MyApplicationTheme {
           ReadingListApp()
       }
   }
   ```
4. Delete the unused directories under `labs/` (`layouts`, `food-ordering`, etc.).
5. Build and run: `.\gradlew.bat assembleDebug`
