package com.example.weather.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weather.components.WeatherListItem
import com.example.weather.viewmodel.WeatherViewModel
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(weatherViewModel: WeatherViewModel = viewModel()) {
    val weatherState by weatherViewModel.weatherState.collectAsState()
    var city by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Погода") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("Введите город") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (city.isNotBlank()) {
                        weatherViewModel.fetchWeatherData(city.trim())
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Поиск")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                when (weatherState) {
                    is WeatherViewModel.WeatherState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    is WeatherViewModel.WeatherState.Error -> {
                        Text(
                            text = (weatherState as WeatherViewModel.WeatherState.Error).message,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    is WeatherViewModel.WeatherState.Success -> {
                        val weatherData = (weatherState as WeatherViewModel.WeatherState.Success).weatherData
                        LazyColumn {
                            item {
                                WeatherListItem(weather = weatherData)
                            }
                        }
                    }
                    is WeatherViewModel.WeatherState.Idle -> {
                        Text(
                            text = "Введите название города и нажмите 'Поиск'",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}
