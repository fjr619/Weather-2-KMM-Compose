package presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.model.forecast.WeatherInfo

@Composable
fun RowScope.SunSetWeatherItem(modifier: Modifier = Modifier, weatherInfo: WeatherInfo) {
    Card(modifier = Modifier.weight(1f)) {
        Column(
            Modifier.padding(16.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Sunrise",
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(
                text = weatherInfo.sunrise,
                style = MaterialTheme.typography.displayMedium,
            )
            Text(
                text = "Sunset ${weatherInfo.sunset}",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}