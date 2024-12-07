package com.example.weather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.BuildConfig
import com.example.weather.model.WeatherResponse
import com.example.weather.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val _weatherState = MutableStateFlow<WeatherState>(WeatherState.Idle)
    val weatherState: StateFlow<WeatherState> = _weatherState

    fun fetchWeatherData(city: String) {
        viewModelScope.launch {
            _weatherState.value = WeatherState.Loading
            try {
                val weatherResponse: WeatherResponse = RetrofitInstance.api.getWeatherData(
                    cityName = city,
                    apiKey = BuildConfig.OPEN_WEATHER_API_KEY
                )
                _weatherState.value = WeatherState.Success(weatherResponse)
            } catch (e: Exception) {
                _weatherState.value = WeatherState.Error("Ошибка при загрузке данных: ${e.localizedMessage}")
            }
        }
    }

    sealed class WeatherState {
        object Idle : WeatherState()
        object Loading : WeatherState()
        data class Success(val weatherData: WeatherResponse) : WeatherState()
        data class Error(val message: String) : WeatherState()
    }
}
