package com.example.weather

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val current: CurrentWeather
)

data class CurrentWeather(
    @SerializedName("temperature_2m")
    val temperature: Double
)