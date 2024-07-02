package data.mapper.to_domain

import domain.Util
import domain.model.forecast.WeatherInfoItem
import data.model.CurrentWeather as DataCurrentWeather
import domain.model.forecast.CurrentWeather as DomainCurrentWeather

class CurrentWeatherMapper : MapperToDomain<DomainCurrentWeather, DataCurrentWeather> {
    override fun mapToDomain(entity: DataCurrentWeather): DomainCurrentWeather {
        return DomainCurrentWeather(
            temperature = entity.temperature2m,
            time = parseTime(entity.time),
            weatherStatus = parseWeatherStatus(entity.weatherCode),
            windDirection = parseWindDirection(entity.windDirection10m),
            windSpeed = entity.windSpeed10m,
            isDay = entity.isDay == 1
        )
    }

    private fun parseTime(time: Long): String {
        return Util.formatUnixDate("MMM,d", time)
    }

    private fun parseWeatherStatus(code: Int): WeatherInfoItem {
        return Util.getWeatherInfo(code)
    }

    private fun parseWindDirection(windDirection: Double): String {
        return Util.getWindDirection(windDirection)
    }
}