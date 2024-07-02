package domain.repository

import domain.model.Response
import domain.model.forecast.Weather
import domain.model.location.DeviceLocation
import kotlinx.coroutines.flow.Flow

interface ForecastRepository {
    fun getForecastWeather(deviceLocation: DeviceLocation): Flow<Response<Weather>>
}