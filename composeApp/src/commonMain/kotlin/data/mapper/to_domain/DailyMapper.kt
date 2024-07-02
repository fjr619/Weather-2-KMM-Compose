package data.mapper.to_domain

import data.model.DailyWeather
import domain.Util
import domain.model.forecast.Daily
import domain.model.forecast.WeatherInfoItem

class DailyMapper : MapperToDomain<Daily, DailyWeather> {
    override fun mapToDomain(entity: DailyWeather): Daily {
        return Daily(
            temperatureMax = entity.temperature2mMax,
            temperatureMin = entity.temperature2mMin,
            time = parseTime(entity.time),
            weatherStatus = parseWeatherStatus(entity.weatherCode),
            windDirection = parseWeatherDirection(entity.windDirection10mDominant),
            sunset = entity.sunset.map { Util.formatUnixDate("HH:mm", it.toLong()) },
            sunrise = entity.sunrise.map { Util.formatUnixDate("HH:mm", it.toLong()) },
            uvIndex = entity.uvIndexMax,
            windSpeed = entity.windSpeed10mMax
        )
    }

    private fun parseTime(time: List<Long>): List<String> = time.map {
        Util.formatUnixDate("E", it)
    }

    private fun parseWeatherStatus(code: List<Int>): List<WeatherInfoItem> {
        return code.map {
            Util.getWeatherInfo(it)
        }
    }

    private fun parseWeatherDirection(windDirections: List<Double>): List<String> {
        return windDirections.map {
            Util.getWindDirection(it)
        }
    }
}