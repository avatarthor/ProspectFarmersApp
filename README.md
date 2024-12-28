# Prospect Farmers App

A Kotlin-based Android application for managing prospect farmers with offline-first capabilities and optional Laravel backend synchronization.

## Demo Login Details (Firebase)
- Email: admin@goodnatureagro.com
- Password: 12345678

## Features

- Add and manage prospect farmers
- Offline-first functionality
- Local data storage using Room Database
- Optional sync with Laravel backend
- Material Design UI components
- Firebase Authentication
- MVVM Architecture

## Prerequisites

- Android Studio Hedgehog or later
- JDK 8
- Kotlin 1.9.0 or higher
- Minimum SDK: API 26 (Android 8.0)
- Target SDK: API 34 (Android 14)
- Compose Compiler Version: 1.5.1
- Laravel backend server (optional for sync functionality)

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/YourUsername/ProspectFarmersApp.git
cd ProspectFarmersApp
```

### 2. API Configuration

1. Open `Constants.kt` in the `util` package
2. Update the `BASE_URL` constant with your Laravel backend URL:
```kotlin
const val BASE_URL = "http://your-backend-url/api/"
```
3. Configure your API key in the same file:
```kotlin
const val API_KEY = "your-api-key"
```

### 4. Backend API Endpoints

The following API endpoints are available for synchronization:

```
GET    /api/farmers         - Fetch all farmers
POST   /api/farmers        - Create a new farmer
PUT    /api/farmers/{id}   - Update a farmer
DELETE /api/farmers/{id}   - Delete a farmer
```

### 5. Building the Project

1. Open the project in Android Studio
2. Sync project with Gradle files
3. Build the project using Build > Make Project
4. Run on an emulator or physical device

## Project Architecture

### MVVM Architecture Components:

- **View Layer**: Activities, Fragments, and Composables
- **ViewModel Layer**: Manages UI-related data and business logic
- **Repository Layer**: Handles data operations and synchronization
- **Room Database**: Local storage for offline-first functionality
- **Retrofit**: Network communication with Laravel backend

### Key Libraries and Versions

#### Core Android & Compose
- AndroidX Core KTX: 1.12.0
- Lifecycle Runtime KTX: 2.7.0
- Activity Compose: 1.8.2
- Compose BOM: 2024.02.00
- Navigation Compose: 2.7.7
- Material3

#### Firebase
- Firebase BOM: 32.7.2
- Firebase Auth KTX
- Google Play Services Auth: 20.7.0

#### Dependency Injection
- Hilt: 2.50
- Hilt Navigation Compose: 1.1.0

#### Database
- Room: 2.6.1

#### Networking
- Retrofit: 2.9.0
- OkHttp Logging Interceptor: 4.11.0
- Gson Converter: 2.9.0

#### Asynchronous Programming
- Kotlinx Coroutines Android: 1.7.3
- Kotlinx Coroutines Play Services: 1.7.3

#### Testing
- JUnit: 4.13.2
- AndroidX Test Ext: 1.1.5
- Espresso Core: 3.5.1

## Data Models

### Farmer Entity
```kotlin
data class Farmer(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val phoneNumber: String,
    val location: String,
    val syncStatus: Boolean = false
)
```

## Offline-First Implementation

The app implements an offline-first architecture where:

1. All data is primarily stored in the local Room database
2. Changes are queued for synchronization when offline
3. Automatic sync occurs when internet connection is available
4. Conflict resolution favors the most recent change

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Troubleshooting

### Common Issues:

1. **Sync Failed Error**: Verify your internet connection and API endpoint configuration
2. **Build Errors**: Ensure all dependencies are properly added and Gradle sync is completed
3. **Firebase Authentication Issues**: Check `google-services.json` placement and Firebase console configuration

For additional support, please open an issue in the GitHub repository.
