package presentation.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.model.forecast.CurrentWeather
import org.jetbrains.compose.resources.painterResource
import presentation.ui.Util

@Composable
fun CurrentWeatherItem(
    modifier: Modifier = Modifier,
    currentWeather: CurrentWeather
) {


    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(resource = currentWeather.weatherStatus.icon),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = currentWeather.temperature.toString() + Util.degreeTxt,
            style = MaterialTheme.typography.displayMedium,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Weather Status:${currentWeather.weatherStatus.info}",
            style = MaterialTheme.typography.bodyLarge,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Wind speed:${currentWeather.windSpeed} Km/h ${currentWeather.windDirection} ",
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}