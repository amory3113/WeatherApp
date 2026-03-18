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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.weather.R
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
            SmallDetailCard(Icons.Default.WbSunny, stringResource(id = R.string.uv_index), "${details.uvIndex}", Modifier.weight(1f))
            SmallDetailCard(Icons.Default.Thermostat, stringResource(id = R.string.feels_like), "${details.feelsLike}°", Modifier.weight(1f))
            SmallDetailCard(Icons.Default.WaterDrop, stringResource(id = R.string.humidity), "${details.humidity}%", Modifier.weight(1f))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SmallDetailCard(Icons.Default.Air, "${details.windDirectionStr} ${stringResource(id = R.string.wind)}", "${details.windSpeed} ${stringResource(id = R.string.km_h)}", Modifier.weight(1f))
            SmallDetailCard(Icons.Default.Compress, stringResource(id = R.string.pressure), "${details.pressureMmHg} ${stringResource(id = R.string.mmHg)}", Modifier.weight(1f))
            SmallDetailCard(Icons.Default.Visibility, stringResource(id = R.string.visibility), "${details.visibilityKm} ${stringResource(id = R.string.km)}", Modifier.weight(1f))
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
            Icon(imageVector = icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))

            Column {
                Text(text = title, color = Color.LightGray, fontSize = 12.sp, fontFamily = OpenSans, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(text = value, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = OpenSans, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}