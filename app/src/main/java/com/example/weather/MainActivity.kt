package com.example.weather

import android.Manifest
import android.annotation.SuppressLint
import android.location.Geocoder
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import com.example.weather.ui.theme.WeatherTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Locale


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewModel = WeatherViewModel()

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            val locationPermissionsLauncher =
                rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                    if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
                        getLocation(fusedLocationClient) { lat, lon ->
                            viewModel.fetchWeather(lat, lon)
                            try {
                                val geocoder = Geocoder(this@MainActivity, Locale.getDefault())
                                @Suppress("DEPRECATION")
                                val addresses = geocoder.getFromLocation(lat, lon, 1)
                                if(!addresses.isNullOrEmpty()){
                                    val city = addresses[0].locality
                                    viewModel.setCity(city ?: "Unknown city")
                                } else {
                                    viewModel.setCity("Unknown city")
                                }
                            } catch (e: Exception) {
                                viewModel.setCity("Searching error")
                            }
                        }
                    } else {
                        viewModel.showLocationError("Location permission denied")
                    }
                }

            LaunchedEffect(Unit) {
                locationPermissionsLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                )
            }
            WeatherTheme {
                WeatherScreen(viewModel)
            }
        }
    }
        @SuppressLint("MissingPermission")
        fun getLocation(client: FusedLocationProviderClient, onLocationReceived: (Double, Double) -> Unit) {
            client.lastLocation.addOnSuccessListener { location ->
                if(location != null) {
                    onLocationReceived(location.latitude, location.longitude)
                }
            }
        }
    }



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    WeatherTheme {

    }
}