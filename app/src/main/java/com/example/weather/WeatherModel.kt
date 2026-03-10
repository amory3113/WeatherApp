package com.example.weather

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val current: CurrentWeather,
    val hourly: HourlyWeather,
    val daily: DailyWeather
)
data class CurrentWeather(
    @SerializedName("temperature_2m") val temperature: Double,
    @SerializedName("weather_code") val weatherCode: Int,
    @SerializedName("is_day") val isDay: Int
)
data class HourlyWeather(
    val time: List<String>,
    @SerializedName("temperature_2m") val temperatures: List<Double>,
    @SerializedName("weather_code") val weatherCodes: List<Int>,
    @SerializedName("is_day") val isDay: List<Int>
)
data class DailyWeather(
    val time: List<String>,
    @SerializedName("temperature_2m_max") val maxTemps: List<Double>,
    @SerializedName("temperature_2m_min") val minTemps: List<Double>,
    @SerializedName("weather_code") val weatherCodes: List<Int>
)

data class AirQualityResponse(
    val current: CurrentAirQuality
)

data class CurrentAirQuality(
    @SerializedName("air_quality")
    val aqi: Int
)