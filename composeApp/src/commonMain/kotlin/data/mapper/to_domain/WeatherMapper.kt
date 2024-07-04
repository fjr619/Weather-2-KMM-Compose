package data.mapper.to_domain

import data.model.DailyWeather
import data.model.HourlyWeather
import data.remote.response.forecast.ForecastResponse
import domain.model.forecast.Daily
import domain.model.forecast.Hourly
import domain.model.forecast.Weather
import data.model.CurrentWeather as DataCurrentWeather
import domain.model.forecast.CurrentWeather as DomainCurrentWeather

class WeatherMapper(
    private val apiDailyMapper: MapperToDomain<Daily, DailyWeather>,
    private val apiCurrentWeatherMapper: MapperToDomain<DomainCurrentWeather, DataCurrentWeather>,
    private val apiHourlyMapper: MapperToDomain<Hourly, HourlyWeather>,
) : MapperToDomain<Weather, ForecastResponse> {
    override fun mapToDomain(entity: ForecastResponse): Weather {
        return Weather(
            latitude = entity.latitude,
            longitude = entity.longitude,
            currentWeather = apiCurrentWeatherMapper.mapToDomain(entity.current),
            daily = apiDailyMapper.mapToDomain(entity.daily),
            hourly = apiHourlyMapper.mapToDomain(entity.hourly)
        )
    }
}