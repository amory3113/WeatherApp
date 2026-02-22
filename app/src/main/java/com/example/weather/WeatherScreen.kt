package com.example.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFF1B2233)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Radom",
            color = Color.White,
            fontSize = 32.sp
        )
        Text(
            text = viewModel.temperature,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Light
        )
    }
}