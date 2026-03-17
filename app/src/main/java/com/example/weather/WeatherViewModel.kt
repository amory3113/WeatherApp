package com.example.weather

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Dehaze
import androidx.compose.material.icons.filled.Thunderstorm
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import java.time.format.TextStyle
import androidx.lifecycle.viewModelScope
import com.example.weather.ui.theme.GradientBrightDay
import com.example.weather.ui.theme.GradientDeepNight
import com.example.weather.ui.theme.GradientDefault
import com.example.weather.ui.theme.GradientOvercastDay
import com.example.weather.ui.theme.GradientOvercastNight
import com.example.weather.ui.theme.GradientRainy
import com.example.weather.ui.theme.GradientSnowy
import com.example.weather.ui.theme.GradientStormy
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.util.Locale
import kotlin.math.roundToInt

data class HourlyInfo(
    val time: String,
    val temp: Int,
    val icon: ImageVector,
    val tint: Color
)

data class DailyInfo(
    val date: String,
    val dayName: String,
    val maxTemp: Int,
    val minTemp: Int,
    val icon: ImageVector,
    val tint: Color
)

data class WeatherDetails(
    val uvIndex: Int = 0,
    val feelsLike: Int = 0,
    val humidity: Int = 0,
    val windSpeed: Int = 0,
    val windDirectionStr: String = "",
    val pressureMmHg: Int = 0,
    val visibilityKm: Int = 0
)

data class SunPhaseInfo(
    val isDay: Boolean = true,
    val leftLabel: String = "Sunrise",
    val rightLabel: String = "Sunset",
    val leftTime: String = "00:00",
    val rightTime: String = "00:00",
    val progress: Float = 0f
)
class WeatherViewModel : ViewModel() {

    var aqiValue by mutableStateOf(0)
        private set
    var aqiDescription by mutableStateOf("Loading...")
        private set
    private val api = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApiService::class.java)
    var temperature by mutableStateOf("Loading...")
        private set
    var dailyForecast by mutableStateOf<List<DailyInfo>>(emptyList())
        private set
    var weatherDetails by mutableStateOf(WeatherDetails())
        private set
    var sunPhase by mutableStateOf(SunPhaseInfo())
        private set
    var isLoading by mutableStateOf(false)
    private set
    var bgColors by mutableStateOf(GradientDefault)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var isRefreshing by mutableStateOf(false)
        private set
    var searchResults by mutableStateOf<List<CityResult>>(emptyList())
        private set
    private var searchJob: Job? = null
    private var currentLat = 0.0
    private var currentLon = 0.0
    fun refreshWeather(){
        isRefreshing = true
        fetchWeather(currentLat, currentLon)
    }
    fun fetchWeather(lat: Double, lon: Double) {
        currentLat = lat
        currentLon = lon
        if (!isRefreshing) isLoading = true
        errorMessage = null
        viewModelScope.launch {
            try {
                val response = api.getWeather(lat = lat, lon = lon)

                val currentTemp = response.current.temperature.roundToInt()
                val currentHourPrefix = response.current.time.substringBefore(":")

                val startIndex = response.hourly.time.indexOfFirst { it.startsWith(currentHourPrefix) }.coerceAtLeast(0)

                val timeList = response.hourly.time.subList(startIndex + 1, startIndex + 25)
                val tempList = response.hourly.temperatures.subList(startIndex + 1, startIndex + 25)
                val codeList = response.hourly.weatherCodes.subList(startIndex + 1, startIndex + 25)
                val isDayList = response.hourly.isDay.subList(startIndex + 1, startIndex + 25)

                val forecastPart = timeList.indices.map { i ->
                    HourlyInfo(
                        time = timeList[i].substringAfter("T"),
                        temp = tempList[i].roundToInt(),
                        icon = getWeatherIcon(codeList[i], isDayList[i]),
                        tint = getWeatherColor(codeList[i])
                    )
                }

                val currentIcon = getWeatherIcon(response.current.weatherCode, response.current.isDay)
                val currentColor = getWeatherColor(response.current.weatherCode)
                hourlyForecast = listOf(HourlyInfo("Now", currentTemp, currentIcon, currentColor)) + forecastPart
                temperature = "$currentTemp"

                val daily = response.daily
                dailyForecast = daily.time.indices.map { i ->
                    val localDate = LocalDate.parse(daily.time[i])
                    val dayOfWeek = localDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)

                    val dayLabel = when (localDate) {
                        LocalDate.now() -> "Today"
                        LocalDate.now().plusDays(1) -> "Tomorrow"
                        else -> dayOfWeek
                    }

                    DailyInfo(
                        date = "${localDate.month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)} ${localDate.dayOfMonth}",
                        dayName = dayLabel,
                        maxTemp = daily.maxTemps[i].roundToInt(),
                        minTemp = daily.minTemps[i].roundToInt(),
                        icon = getWeatherIcon(daily.weatherCodes[i], 1),
                        tint = getWeatherColor(daily.weatherCodes[i])
                    )
                }

                val current = response.current

                val directions = listOf("N", "NE", "E", "SE", "S", "SW", "W", "NW")
                val dirIndex = (((current.windDirection + 22.5) / 45) % 8).toInt()
                val windDirString = directions[dirIndex]

                weatherDetails = WeatherDetails(
                    uvIndex = current.currentUvIndex.roundToInt(),
                    feelsLike = current.feelsLike.roundToInt(),
                    humidity = current.humidity,
                    windSpeed = current.windSpeed.roundToInt(),
                    windDirectionStr = windDirString,
                    pressureMmHg = (current.pressure * 0.750062).roundToInt(),
                    visibilityKm = (current.visibility / 1000).roundToInt()
                )

                val isDaytime = current.isDay == 1
                val todaySunrise = response.daily.sunrise.first().substringAfter("T")
                val todaySunset = response.daily.sunset.first().substringAfter("T")
                val tomorrowSunrise = response.daily.sunrise.getOrNull(1)?.substringAfter("T") ?: todaySunrise

                fun timeToMin(t: String) = t.split(":")[0].toInt() * 60 + t.split(":")[1].toInt()

                val serverTimeOnly = current.time.substringAfter("T")
                val nowMin = timeToMin(serverTimeOnly)

                sunPhase = if (isDaytime) {
                    val startMin = timeToMin(todaySunrise)
                    val endMin = timeToMin(todaySunset)
                    val prog = (nowMin - startMin).toFloat() / (endMin - startMin).toFloat()

                    SunPhaseInfo(
                        isDay = true,
                        leftLabel = "Sunrise", rightLabel = "Sunset",
                        leftTime = todaySunrise, rightTime = todaySunset,
                        progress = prog.coerceIn(0f, 1f)
                    )
                } else {
                    val startMin = timeToMin(todaySunset)
                    val endMin = timeToMin(tomorrowSunrise)

                    val adjustedNow = if (nowMin < startMin) nowMin + (24 * 60) else nowMin
                    val adjustedEnd = if (endMin < startMin) endMin + (24 * 60) else endMin

                    val prog = (adjustedNow - startMin).toFloat() / (adjustedEnd - startMin).toFloat()

                    SunPhaseInfo(
                        isDay = false,
                        leftLabel = "Sunset", rightLabel = "Sunrise",
                        leftTime = todaySunset, rightTime = tomorrowSunrise,
                        progress = prog.coerceIn(0f, 1f)
                    )
                }

                val code = current.weatherCode
                val isDay = current.isDay == 1
                bgColors = when (code) {
                    0, 1 -> if (isDay) GradientBrightDay else GradientDeepNight
                    2, 3 -> if (isDay) GradientOvercastDay else GradientOvercastNight
                    in 51..67, in 80..82 -> GradientRainy
                    in 71..77, 85, 86 -> GradientSnowy
                    in 95..99 -> GradientStormy
                    else -> GradientDefault
                }

            } catch (e: Exception) {
                temperature = "Error ${e.message}"
                errorMessage = e.message
            } finally {
                isLoading = false
                isRefreshing = false
            }

            val aqResponse = api.getAirQuality(lat = lat, lon = lon)

            aqiValue = aqResponse.current.aqi ?: 0

            aqiDescription = when (aqiValue) {
                in 0..50 -> "Good"
                in 51..100 -> "Moderate"
                in 101..150 -> "Poor"
                in 151..200 -> "Unhealthy"
                in 201..300 -> "Very Poor"
                else -> "Hazardous"
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
    fun getCitySuggestions(query: String) {
        searchJob?.cancel()
        if (query.isBlank()) {
            searchResults = emptyList()
            return
        }

        searchJob = viewModelScope.launch {
            delay(100)
            try {
                val response = api.searchCity(query)
                searchResults = response.results ?: emptyList()
            } catch (e: Exception) {
                searchResults = emptyList()
            }
        }
    }
    fun clearSuggestions() {
        searchResults = emptyList()
    }
    fun selectCity(city: CityResult) {
        searchResults = emptyList()
        isLoading = true
        errorMessage = null

        cityName = if (city.country != null) "${city.name}, ${city.country}" else city.name

        fetchWeather(city.latitude, city.longitude)
    }
    var hourlyForecast by mutableStateOf<List<HourlyInfo>>(emptyList())
        private set
    private fun getWeatherIcon(code: Int, isDay: Int = 1): ImageVector {
        return when (code) {
            0, 1 -> if (isDay == 1) Icons.Default.WbSunny else Icons.Default.DarkMode
            2, 3 -> Icons.Default.Cloud
            45, 48 -> Icons.Default.Dehaze
            51, 53, 55, 56, 57, 61, 63, 65, 66, 67, 80, 81, 82 -> Icons.Default.WaterDrop
            71, 73, 75, 77, 85, 86 -> Icons.Default.AcUnit
            95, 96, 99 -> Icons.Default.Thunderstorm
            else -> Icons.Default.Cloud
        }
    }
    private fun getWeatherColor(code: Int): Color {
        return when (code){
            0, 1 -> Color.Yellow
            else -> Color.White
        }
    }
}

