package presentation.ui.daily

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import domain.model.forecast.Weather
import domain.model.forecast.WeatherInfo
import org.jetbrains.compose.resources.painterResource
import presentation.ui.Util
import presentation.ui.components.SunSetWeatherItem
import presentation.ui.components.UvIndexWeatherItem
import weathercmp.composeapp.generated.resources.Res
import weathercmp.composeapp.generated.resources.wind_ic

@Composable
fun DailyScreen(
    modifier: Modifier = Modifier,
    weather: Weather?
) {

    var selectedWeatherIndex by remember { mutableIntStateOf(0) }
    val currentDailyWeatherItem = remember(key1 = selectedWeatherIndex, key2 = weather) {
        weather?.daily?.weatherInfo?.get(selectedWeatherIndex)
    }

    DailyContent(
        modifier = modifier,
        weather = weather,
        selectedWeatherIndex = selectedWeatherIndex,
        currentDailyWeatherItem = currentDailyWeatherItem,
        onUpdateSelectedWeatherIndex = { index -> selectedWeatherIndex = index }
    )
}

@Composable
fun DailyContent(
    modifier: Modifier = Modifier,
    weather: Weather?,
    selectedWeatherIndex: Int,
    currentDailyWeatherItem: WeatherInfo?,
    onUpdateSelectedWeatherIndex: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        currentDailyWeatherItem?.let {
            Text(
                text = "Max ${it.temperatureMax} Min ${it.temperatureMin}",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "7 Days Forecast",
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow {
            weather?.daily?.let { daily ->
                itemsIndexed(items = daily.weatherInfo) { index, weatherInfo ->
                    val selectedColor =
                        if (selectedWeatherIndex == index) MaterialTheme.colorScheme.inverseSurface
                        else CardDefaults.cardColors().containerColor
                    DailyWeatherInfoItem(
                        weatherInfo = weatherInfo,
                        backgroundColor = selectedColor,
                        onClick = {
                            onUpdateSelectedWeatherIndex(index)
                        }
                    )

                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        currentDailyWeatherItem?.let {
            Card {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(resource = Res.drawable.wind_ic),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "WIND", style = MaterialTheme.typography.bodyMedium)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = it.windSpeed.toString() + " Km/h " + it.windDirection,
                        style = MaterialTheme.typography.headlineMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SunSetWeatherItem(weatherInfo = it)
                Spacer(modifier = Modifier.size(5.dp))
                UvIndexWeatherItem(weatherInfo = it)
            }
        }
    }
}

@Composable
fun DailyWeatherInfoItem(
    modifier: Modifier = Modifier,
    weatherInfo: WeatherInfo,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier.padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${weatherInfo.temperatureMax} ${Util.degreeTxt}",
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Icon(
                painter = painterResource(resource = weatherInfo.weatherStatus.icon),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = weatherInfo.time,
                style = MaterialTheme.typography.bodySmall,
            )

        }

    }

}