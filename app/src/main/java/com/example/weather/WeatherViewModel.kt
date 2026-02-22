package com.example.weather

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
                temperature = "$tempInt°C"
            } catch (e: Exception) {
                temperature = "Error ${e.message}"
            }
        }
    }

    fun showLocationError(message: String){
        temperature = message
    }
}