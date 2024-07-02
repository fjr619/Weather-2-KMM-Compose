package domain.model.forecast

data class HourlyInfoItem(
    val temperature: Double,
    val time: String,
    val weatherStatus: WeatherInfoItem
)