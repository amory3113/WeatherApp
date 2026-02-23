package com.example.weather

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val current: CurrentWeather,
    val hourly: HourlyWeather
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