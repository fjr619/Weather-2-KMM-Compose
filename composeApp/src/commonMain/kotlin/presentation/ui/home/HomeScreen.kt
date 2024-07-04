package presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.model.forecast.Weather
import org.koin.core.annotation.KoinExperimentalAPI
import presentation.ui.home.components.CurrentWeatherItem
import presentation.ui.home.components.HourlyWeatherItem
import presentation.ui.components.SunSetWeatherItem
import presentation.ui.components.UvIndexWeatherItem

@OptIn(KoinExperimentalAPI::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    weather: Weather?,
) {
    HomeContent(
        modifier = modifier,
        weather = weather
    )
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    weather: Weather?
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        weather?.let {
            CurrentWeatherItem(currentWeather = it.currentWeather)
            HourlyWeatherItem(
                hourly = it.hourly
            )
        }

        weather?.todayWeatherInfo()?.let {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SunSetWeatherItem(weatherInfo = it)
                Spacer(modifier = Modifier.size(5.dp))
                UvIndexWeatherItem(weatherInfo = it)
            }
        }

    }
}



