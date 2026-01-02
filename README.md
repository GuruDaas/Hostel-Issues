# 🏨 Hostel Issues Management System

## 📋 Project Overview
**Hostel Issues Management System** is a modern, native Android application designed to streamline communication between hostel residents (students) and administration (wardens). Built using **Kotlin** and **Jetpack Compose**, this app provides a seamless platform for reporting, tracking, and resolving maintenance and daily living issues within hostel premises.

This project demonstrates a robust implementation of **MVVM architecture**, **RESTful API integration**, and **Declarative UI** patterns.

---

## ✨ Key Features

### 🔐 Role-Based Authentication
*   **Student Login/Register**: Secure account creation and login for students.
*   **Warden Login**: specialized administrative access for hostel wardens.
*   **Role Selection**: Clean onboarding screen to route users to their respective flows.

### 📢 For Students
*   **Raise Issues**: Intuitive interface to report problems.
*   **Categorization**: Classify issues by type (Electricity, Water, Furniture, etc.).
*   **Detailed Reporting**: Add specific descriptions, urgency levels (Low, Medium, High), and preferred resolution times.
*   **Dashboard**: View the status of raised issues in real-time.

### 🛡️ For Wardens
*   **Admin Dashboard**: comprehensive view of all reported issues.
*   **Issue Tracking**: Monitor issue details, student information, and severity.
*   **Management**: efficient workflow to oversee hostel maintenance.

---

## 🛠️ Technology Stack

This application is built with modern Android development best practices:

| Category | Technology |
| :--- | :--- |
| **Language** | [Kotlin](https://kotlinlang.org/) (100%) |
| **UI Framework** | [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material3) |
| **Architecture** | MVVM (Model-View-ViewModel) |
| **Networking** | Retrofit 2, OkHttp 4, Gson |
| **Concurrency** | Kotlin Coroutines & Flow |
| **Navigation** | Jetpack Navigation Compose |
| **Dependency Injection** | Manual DI / ViewModel Factory pattern |

---

## 📂 Project Structure

The codebase is organized following the **Clean Architecture** principles:

```
com.hostel.Issues
├── api/            # Retrofit interfaces & network clients
├── data/           # Repositories & Data sources
├── models/         # Data classes (Issue, User, etc.)
├── navigation/     # Navigation graph & route definitions
├── ui/             # Jetpack Compose Screens
│   ├── auth/       # Login, Register, Role Selection
│   ├── student/    # Student Dashboard & Issue features
│   ├── warden/     # Warden Dashboard & Management
│   ├── theme/      # App theming & styling
│   └── common/     # Reusable UI components
├── utils/          # Helper functions & constants
└── viewmodel/      # State management (AAC ViewModels)
```

---

## 🚀 Getting Started

### Prerequisites
*   Android Studio Hedgehog | 2023.1.1 or newer
*   JDK 17
*   Android SDK API 34

### Installation
1.  **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/HostelIssuesSystem.git
    ```
2.  **Open in Android Studio:**
    *   File > Open > Select the `HostelIssuesSystem` folder.
3.  **Sync Gradle:**
    *   Allow Gradle to download dependencies.
4.  **Run the App:**
    *   Connect an Android device or start an Emulator.
    *   Click the **Run** ▶️ button.

---

## 🔮 Future Enhancements
*   **Image Attachment**: Allow students to upload photos of the issues.
*   **Push Notifications**: Notify students when issue status changes.
*   **Offline Mode**: Cache issues locally using Room Database.
*   **Chat System**: Direct communication channel between Student and Warden.

---

*Developed by Aman*
