package com.example.weather

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val current: CurrentWeather,
    val hourly: HourlyWeather,
    val daily: DailyWeather
)

data class GeocodingResponse(
    val results: List<CityResult>?
)

data class CityResult(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String?
)
data class CurrentWeather(
    val time: String,
    @SerializedName("temperature_2m") val temperature: Double,
    @SerializedName("weather_code") val weatherCode: Int,
    @SerializedName("is_day") val isDay: Int,
    @SerializedName("relative_humidity_2m") val humidity: Int,
    @SerializedName("apparent_temperature") val feelsLike: Double,
    @SerializedName("pressure_msl") val pressure: Double,
    @SerializedName("wind_speed_10m") val windSpeed: Double,
    @SerializedName("wind_direction_10m") val windDirection: Int,
    val visibility: Double,
    @SerializedName("uv_index") val currentUvIndex: Double
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
    @SerializedName("weather_code") val weatherCodes: List<Int>,
    @SerializedName("uv_index_max") val uvIndexMax: List<Double>,
    val sunrise: List<String>,
    val sunset: List<String>
)

data class AirQualityResponse(
    val current: CurrentAirQuality
)

data class CurrentAirQuality(
    @SerializedName("us_aqi") val aqi: Int?
)