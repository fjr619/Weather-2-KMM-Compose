package domain.model.forecast

data class WeatherInfo(
    val temperatureMax: Double,
    val temperatureMin: Double,
    val time: String,
    val weatherStatus: WeatherInfoItem,
    val windDirection: String,
    val windSpeed: Double,
    val sunrise: String,
    val sunset: String,
    val uvIndex: Double,
)