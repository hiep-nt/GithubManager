# **GithubManger App**

This is a simple Android app that can show the list of Github Users and their detailed information.

List users will be shown in the User List screen with pagination and when user clicks on any item, app will navigate to the user's detail screen

## **Architecture**

This project follows Clean Architecture with 3 modules:
1. **Data**: This module contains app's Data sources (API, Database) and Repository implementation
2. **Domain**: Contains Usecase classes which implement the business login with Repository interface
3. **App**: This is presentation layer. Implemented with MVVM Architeture

## **Third-party libraries**

- **Retrofit** for network api interaction
- **Room** for local database
- **Hilt** for dependencies injection
- **Jetpack Compose** for UI
- **Coil** for load image from url
- **Kotlin Coroutine, Kotlin flow** for handle asynchronous
- **JUnit, Mockk** for Unit testing

## **Demo**
[Github_manager_demo_video.webm](https://github.com/user-attachments/assets/d13a04a3-83c8-4626-8dc1-56f96483e5a4)
