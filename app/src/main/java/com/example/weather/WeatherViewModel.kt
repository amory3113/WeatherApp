package com.example.weather

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
//import androidx.compose.ui.text.TextStyle
import androidx.lifecycle.ViewModel
import java.time.format.TextStyle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt

data class HourlyInfo(
    val time: String,
    val temp: Int
)

data class DailyInfo(
    val date: String,
    val dayName: String,
    val maxTemp: Int,
    val minTemp: Int
)
class WeatherViewModel : ViewModel() {
    private val api = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApiService::class.java)
    var temperature by mutableStateOf("Loading...")
        private set

    var dailyForecast by mutableStateOf<List<DailyInfo>>(emptyList())
        private set

    fun fetchWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                val response = api.getWeather(lat = lat, lon = lon)

                val currentTemp = response.current.temperature.roundToInt()
                val currentHour = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0)
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
                val currentHourString = currentHour.format(formatter)

                val startIndex = response.hourly.time.indexOfFirst { it == currentHourString }.coerceAtLeast(0)

                val timeList = response.hourly.time.subList(startIndex + 1, startIndex + 25)
                val tempList = response.hourly.temperatures.subList(startIndex + 1, startIndex + 25)

                val forecastPart = timeList.zip(tempList) { timeString, tempDouble ->
                    HourlyInfo(
                        time = timeString.substringAfter("T"),
                        temp = tempDouble.roundToInt()
                    )
                }
                hourlyForecast = listOf(HourlyInfo("Now", currentTemp)) + forecastPart
                temperature = "$currentTemp"

                // ---> ВОТ СЮДА МЫ ПЕРЕНЕСЛИ DAILY ПРОГНОЗ <---
                val daily = response.daily
                dailyForecast = daily.time.indices.map { i ->
                    // Исправили parse через 's'
                    val localDate = LocalDate.parse(daily.time[i])
                    // Исправили W в dayOfWeek
                    val dayOfWeek = localDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)

                    val dayLabel = when (localDate) {
                        LocalDate.now() -> "Today"
                        LocalDate.now().plusDays(1) -> "Tomorrow" // Исправили L и слово Tomorrow
                        else -> dayOfWeek
                    }

                    DailyInfo(
                        date = "${localDate.month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)} ${localDate.dayOfMonth}",
                        dayName = dayLabel,
                        maxTemp = daily.maxTemps[i].roundToInt(),
                        minTemp = daily.minTemps[i].roundToInt()
                    )
                }

            } catch (e: Exception) {
                temperature = "Error ${e.message}"
            }
        }
    }
    fun showLocationError(message: String){
        temperature = message
    }
    var cityName by mutableStateOf("Loading...")
        private set
    fun setCity(city: String) {
        cityName = city
    }
    var hourlyForecast by mutableStateOf<List<HourlyInfo>>(emptyList())
        private set
}

