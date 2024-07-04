This is a Kotlin Multiplatform project targeting Android, iOS.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…

Android

<img src="https://github.com/fjr619/Weather-2-KMM-Compose/assets/9444636/d3f73a35-05df-4978-aeab-9d3740ff14fd" height="500"> <img src="https://github.com/fjr619/Weather-2-KMM-Compose/assets/9444636/2e9a7151-1e54-41a6-a0c7-f5fd36627445" height="500">
