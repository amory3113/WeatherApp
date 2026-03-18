package com.example.weather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather.DailyInfo
import com.example.weather.ui.theme.OpenSans

@Composable
fun DailyForecastCard(dailyList: List<DailyInfo>){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.Black.copy(alpha = 0.2f), RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            dailyList.forEach { day ->
                DailyForecastRow(day)
            }
        }
    }
}

@Composable
fun DailyForecastRow(day: DailyInfo){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(day.date, modifier = Modifier.weight(1.5f), color = Color.White, fontSize = 14.sp, fontFamily = OpenSans, fontWeight = FontWeight.Bold)
        Text(day.dayName.asString(), modifier = Modifier.weight(2.5f), color = Color.White, fontSize = 16.sp, fontFamily = OpenSans, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Icon(
            imageVector = day.icon,
            contentDescription = null,
            tint = day.tint,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "${day.minTemp}° / ${day.maxTemp}°",
            modifier = Modifier.weight(2f),
            color = Color.White,
            fontSize = 16.sp,
            fontFamily = OpenSans,
            textAlign = TextAlign.End
        )
    }
}