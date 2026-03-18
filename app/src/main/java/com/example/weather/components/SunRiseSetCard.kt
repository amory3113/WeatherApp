package com.example.weather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather.SunPhaseInfo
import com.example.weather.ui.theme.OpenSans

@Composable
fun SunRiseSetCard(info: SunPhaseInfo) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.Black.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = info.leftLabel.asString(), color = Color.White, fontSize = 14.sp, fontFamily = OpenSans)
                Text(text = info.rightLabel.asString(), color = Color.White, fontSize = 14.sp, fontFamily = OpenSans)
            }

            Spacer(modifier = Modifier.height(16.dp))

            val iconBias = (info.progress * 2f) - 1f

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .background(Color.DarkGray, RoundedCornerShape(50))
                )

                Icon(
                    imageVector = if (info.isDay) Icons.Default.WbSunny else Icons.Default.DarkMode,
                    contentDescription = null,
                    tint = if (info.isDay) Color.Yellow else Color.White,
                    modifier = Modifier
                        .align(BiasAlignment(horizontalBias = iconBias, verticalBias = 0f))
                        .size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = info.leftTime, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = OpenSans)
                Text(text = info.rightTime, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = OpenSans)
            }
        }
    }
}