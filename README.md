# 🌤️ Weather App

A modern, beautifully designed Android Weather Application built entirely with **Kotlin** and **Jetpack Compose**. This project demonstrates the implementation of a clean UI/UX, network requests, and dynamic animations.

## ✨ Screenshots
<div align="center">
  <img src="https://github.com/user-attachments/assets/5f46ab6a-5fee-492b-8e91-9943cf2b9859" width="250" />
  <img src="https://github.com/user-attachments/assets/518dcdf9-a454-4a8e-8e22-5b9b5f1454a4" width="250" />
  <img src="https://github.com/user-attachments/assets/4e2981b1-1808-42e2-b014-7c6228e38457" width="250" />
</div>

## 🚀 Features

* **🌍 Multi-language Support (i18n):** Automatically adapts the UI, dates, and city search results to the user's system language (Supports English, Russian, Ukrainian, Polish).
* **🔍 Smart Auto-complete Search:** A live dropdown menu suggests cities and countries as you type, preventing ambiguous location errors.
* **⏱️ Timezone-aware Data:** Calculates hourly forecasts and Sun/Moon phases based on the target city's local time, not the user's device time.
* **🎨 Dynamic Backgrounds:** The app background smoothly animates and changes its gradient based on the current weather conditions.
* **🌬️ Air Quality Index (AQI):** Displays current US AQI with detailed health status.
* **Rich UI/UX:** * Custom Shimmer loading effect instead of boring spinners.
  * Pull-to-Refresh functionality using modern Material 3 components.
  * Glassmorphism-style semi-transparent cards.
  * Robust UI that prevents system "Force Dark Mode" from breaking custom color palettes.
* **Detailed Metrics:** UV Index, Humidity, Wind Direction/Speed, Pressure (converted to mmHg), and Visibility.

## 🛠 Tech Stack & Architecture

This project was built using modern Android development practices:

* **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3)
* **Language:** [Kotlin](https://kotlinlang.org/)
* **Architecture:** MVVM (Model-View-ViewModel) with custom `UiText` wrappers for clean localization handling.
* **Networking:** [Retrofit2](https://square.github.io/retrofit/) & Gson for API calls and JSON parsing
* **Asynchrony:** Kotlin Coroutines & ViewModelScope
* **APIs Used:** [Open-Meteo](https://open-meteo.com/) (Weather Forecast, Air Quality, Geocoding)

## 📱 How to Run the Project

1. Clone this repository:
   ```bash
   git clone [https://github.com/amory3113/WeatherApp.git](https://github.com/amory3113/WeatherApp.git)
2. Open the project in Android Studio.
3. Let Gradle sync and download all dependencies.
4. Run the app on an Emulator or a physical Android device.
(Note: Ensure your device/emulator has internet access and location services enabled).

👨‍💻 Author
Maksym Shevelenko,
Telegram: @amory3113,
Gmail: maksshevelenko@gmail.com
