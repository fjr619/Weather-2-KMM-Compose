package domain.model.forecast

data class Hourly(
    val temperature: List<Double>,
    val time: List<String>,
    val weatherStatus: List<WeatherInfoItem>
) {
    val weatherInfo: List<HourlyInfoItem>
        get() {
            return time.mapIndexed { index, time ->
                HourlyInfoItem(
                    temperature = temperature[index],
                    time = time,
                    weatherStatus = weatherStatus[index]
                )
            }
        }
}