package com.example.weather

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("v1/forecast")
        suspend fun getWeather(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("current") current: String = "temperature_2m,weather_code,is_day,relative_humidity_2m,apparent_temperature,pressure_msl,wind_speed_10m,wind_direction_10m,visibility,uv_index",
        @Query("hourly") hourly: String = "temperature_2m,weather_code,is_day",
        @Query("daily") daily: String = "temperature_2m_max,temperature_2m_min,weather_code,uv_index_max,sunrise,sunset",
        @Query("timezone") timezone: String = "auto"
    ): WeatherResponse

        @GET("https://air-quality-api.open-meteo.com/v1/air-quality")
        suspend fun getAirQuality(
            @Query("latitude") lat: Double,
            @Query("longitude") lon: Double,
            @Query("current") current: String = "us_aqi",
            @Query("timezone") timezone: String = "auto"
        ): AirQualityResponse

        @GET("https://geocoding-api.open-meteo.com/v1/search")
        suspend fun searchCity(
            @Query("name") query: String,
            @Query("count") count: Int = 5,
            @Query("language") language: String = "en",
            @Query("format") format: String = "json"
        ) : GeocodingResponse

}