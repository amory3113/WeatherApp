package com.example.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.test.espresso.base.Default
import com.example.weather.components.AirQualityCard
import com.example.weather.components.DailyForecastCard
import com.example.weather.components.HourlyForecastCard
import com.example.weather.components.SunRiseSetCard
import com.example.weather.components.WeatherDetailsGrid
import com.example.weather.ui.theme.OpenSans
import com.example.weather.ui.theme.WeatherTheme

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1B2233))
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(start = 8.dp, top = 16.dp, end = 8.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = viewModel.cityName,
                fontFamily = OpenSans,
                color = Color.White,
                fontSize = 28.sp
            )
            IconButton(
                onClick = {  }
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    tint = Color.White,
                    painter = painterResource(id = R.drawable.ic_settings_thin),
                    contentDescription = "Settings",
                )
            }
        }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 30.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "°",
                        style = TextStyle(
                            fontFamily = OpenSans,
                            fontSize = 100.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Transparent
                        )
                    )

                    Text(
                        text = viewModel.temperature,
                        fontFamily = OpenSans,
                        color = Color.White,
                        fontSize = 100.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = "°",
                        fontFamily = OpenSans,
                        color = Color.White,
                        fontSize = 100.sp,
                        fontWeight = FontWeight.Light
                    )
                }
                HourlyForecastCard(hourlyList = viewModel.hourlyForecast)
                Spacer(modifier = Modifier.height(12.dp))
                DailyForecastCard(dailyList = viewModel.dailyForecast)
                Spacer(modifier = Modifier.height(12.dp))
                AirQualityCard(
                    aqiValue = viewModel.aqiValue,
                    aqiDescription = viewModel.aqiDescription
                )
                Spacer(modifier = Modifier.height(12.dp))
                WeatherDetailsGrid(details = viewModel.weatherDetails)
                Spacer(modifier = Modifier.height(12.dp))
                SunRiseSetCard(info = viewModel.sunPhase)
            }
        }
    }


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview2() {
    WeatherTheme {
        WeatherScreen(WeatherViewModel())
    }
}