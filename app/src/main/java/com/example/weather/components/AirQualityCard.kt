package com.example.weather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather.ui.theme.OpenSans

@Composable
fun AirQualityCard(aqiValue: Int, aqiDescription: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.Black.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column(

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Air Quality", color = Color.Gray, fontSize = 14.sp, fontFamily = OpenSans)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "$aqiDescription $aqiValue",
                color = Color.LightGray,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                fontFamily = OpenSans
            )

            Spacer(modifier = Modifier.height(16.dp))

            val gradientColors = listOf(
                Color(0xFF00E676),
                Color(0xFFFFEB3B),
                Color(0xFFFF9800),
                Color(0xFFE91E63),
                Color(0xFF9C27B0)
            )
            val safeAqi = aqiValue.coerceIn(0, 100)
            val dotBias = (safeAqi / 100f) * 2f - 1f

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .background(Brush.horizontalGradient(gradientColors), RoundedCornerShape(8.dp))
            ) {
                Box(
                    modifier = Modifier
                        .align(BiasAlignment(horizontalBias = dotBias, verticalBias = 0f))
                        .size(10.dp)
                        .background(Color.White, CircleShape)
                )
            }
        }
    }

}