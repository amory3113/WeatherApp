package com.example.weather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather.HourlyInfo
import com.example.weather.ui.theme.OpenSans

@Composable
fun HourlyForecastCard(hourlyList: List<HourlyInfo>){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.Black.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
            .padding(top = 8.dp, end = 1.dp, bottom = 8.dp)
    ){
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            items(hourlyList){ info ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(text = info.time, color = Color.White, fontSize = 14.sp, fontFamily = OpenSans)
                    Spacer(modifier = Modifier.height(4.dp))
                    Icon(imageVector = info.icon, contentDescription = null, tint = info.tint)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "${info.temp}°", color = Color.White, fontSize = 16.sp, fontFamily = OpenSans, fontWeight = FontWeight.Bold)
                }

            }
        }
    }
}