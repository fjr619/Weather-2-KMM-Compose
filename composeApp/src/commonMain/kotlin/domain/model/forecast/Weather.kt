package domain.model.forecast

import domain.Util

data class Weather(
    val latitude: Double,
    val longitude: Double,
    val currentWeather: CurrentWeather,
    val daily: Daily,
    val hourly: Hourly
) {
    fun todayWeatherInfo() = daily.weatherInfo.find {
        Util.isTodayDate(it.time)
    }
}
