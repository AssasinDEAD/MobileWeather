package com.example.weather.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.weather.model.WeatherResponse
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun WeatherListItem(weather: WeatherResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            val iconUrl = "https://openweathermap.org/img/wn/${weather.weather.firstOrNull()?.icon}@2x.png"
            Image(
                painter = rememberAsyncImagePainter(iconUrl),
                contentDescription = "Weather Icon",
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = weather.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = weather.weather.firstOrNull()?.description?.replaceFirstChar { it.uppercase() }
                        ?: "Нет описания",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Температура: ${weather.main.temp}°C",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Ощущается как: ${weather.main.feelsLike}°C",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Влажность: ${weather.main.humidity}%",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Давление: ${weather.main.pressure} гПа",
                    style = MaterialTheme.typography.bodySmall
                )

                val date = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(Date(weather.dt * 1000))
                Text(
                    text = "Дата: $date",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
