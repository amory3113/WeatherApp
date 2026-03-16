package com.example.weather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather.WeatherDetails
import com.example.weather.ui.theme.OpenSans

@Composable
fun WeatherDetailsGrid(details: WeatherDetails) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SmallDetailCard(Icons.Default.WbSunny, "UV", "${details.uvIndex}", Modifier.weight(1f))
            SmallDetailCard(Icons.Default.Thermostat, "Feels like", "${details.feelsLike}°", Modifier.weight(1f))
            SmallDetailCard(Icons.Default.WaterDrop, "Humidity", "${details.humidity}%", Modifier.weight(1f))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SmallDetailCard(Icons.Default.Air, "${details.windDirectionStr} wind", "${details.windSpeed} km/h", Modifier.weight(1f))
            SmallDetailCard(Icons.Default.Compress, "Pressure", "${details.pressureMmHg} mmHg", Modifier.weight(1f))
            SmallDetailCard(Icons.Default.Visibility, "Visibility", "${details.visibilityKm} km", Modifier.weight(1f))
        }
    }
}

@Composable
fun SmallDetailCard(icon: ImageVector, title: String, value: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .background(Color.Black.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(24.dp))

            Column {
                Text(text = title, color = Color.Gray, fontSize = 12.sp, fontFamily = OpenSans)
                Text(text = value, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = OpenSans)
            }
        }
    }
}