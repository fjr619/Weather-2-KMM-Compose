package data.mapper.to_domain

import data.model.HourlyWeather
import domain.Util
import domain.model.forecast.Hourly
import domain.model.forecast.WeatherInfoItem

class HourlyMapper : MapperToDomain<Hourly, HourlyWeather> {
    override fun mapToDomain(entity: HourlyWeather): Hourly {
        return Hourly(
            temperature = entity.temperature2m,
            time = parseTime(entity.time),
            weatherStatus = parseWeatherStatus(entity.weatherCode)
        )
    }

    private fun parseTime(time: List<Long>): List<String> {
        return time.map {
            Util.formatUnixDate("HH:mm", it)
        }
    }

    private fun parseWeatherStatus(code: List<Int>): List<WeatherInfoItem> {
        return code.map {
            Util.getWeatherInfo(it)
        }
    }
}