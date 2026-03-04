package com.example.weather

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val current: CurrentWeather,
    val hourly: HourlyWeather,
    val daily: DailyWeather
)
data class CurrentWeather(
    @SerializedName("temperature_2m")
    val temperature: Double
)
data class HourlyWeather(
    val time: List<String>,
    @SerializedName("temperature_2m")
    val temperatures: List<Double>
)
data class DailyWeather(
    val time: List<String>,
    @SerializedName("temperature_2m_max")
    val maxTemps: List<Double>,
    @SerializedName("temperature_2m_min")
    val minTemps: List<Double>
)