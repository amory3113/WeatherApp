package com.example.weather

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

data class HourlyInfo(
    val time: String,
    val temp: Int
)
class WeatherViewModel : ViewModel() {
    private val api = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApiService::class.java)
    var temperature by mutableStateOf("Loading...")
        private set
    fun fetchWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                val response = api.getWeather(lat = lat, lon = lon)
                val tempDouble = response.current.temperature
                val tempInt = tempDouble.toInt()
                temperature = "$tempInt"

                val timeList = response.hourly.time.take(24)
                val tempList = response.hourly.temperatures.take(24)
                hourlyForecast = timeList.zip(tempList) {
                    timeString, tempDouble -> val formattedTime = timeString.substringAfter("T")
                    HourlyInfo(
                        time = formattedTime,
                        temp = tempDouble.toInt()
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