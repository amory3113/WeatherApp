package com.example.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.ui.graphics.Brush
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import com.example.weather.components.AirQualityCard
import com.example.weather.components.DailyForecastCard
import com.example.weather.components.HourlyForecastCard
import com.example.weather.components.SunRiseSetCard
import com.example.weather.components.WeatherDetailsGrid
import com.example.weather.refresh.WeatherSkeleton
import com.example.weather.ui.theme.OpenSans
import com.example.weather.ui.theme.WeatherTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    var searchText by remember { mutableStateOf("") }
    val topColor by animateColorAsState(targetValue = viewModel.bgColors[0], animationSpec = tween(1000), label = "topColor")
    val bottomColor by animateColorAsState(targetValue = viewModel.bgColors[1], animationSpec = tween(1000), label = "bottomColor")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(topColor, bottomColor)))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = viewModel.cityName,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold, fontFamily = OpenSans,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(12.dp))

            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = {
                    Text(
                        text = "Search...",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontFamily = OpenSans
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        viewModel.searchCity(searchText)
                        searchText = ""
                    }
                ),
                trailingIcon = {
                    IconButton(onClick = {
                        viewModel.searchCity(searchText)
                        searchText = ""
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.White
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.White.copy(alpha = 0.5f),
                    unfocusedIndicatorColor = Color.White.copy(alpha = 0.2f),
                    focusedContainerColor = Color.Black.copy(alpha = 0.2f),
                    unfocusedContainerColor = Color.Black.copy(alpha = 0.2f)
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.weight(1.3f)
            )

            Spacer(modifier = Modifier.width(8.dp))

        }
        if (viewModel.isLoading && !viewModel.isRefreshing) {
            WeatherSkeleton()
        } else {
            PullToRefreshBox(
                isRefreshing = viewModel.isRefreshing,
                onRefresh = { viewModel.refreshWeather() },
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(top = 30.dp, bottom = 32.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row(verticalAlignment = Alignment.Top) {
                        Text(
                            text = "°",
                            style = TextStyle(
                                fontFamily = OpenSans,
                                fontSize = 100.sp,
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
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview2() {
    WeatherTheme {
        WeatherScreen(WeatherViewModel())
    }
}