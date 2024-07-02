package data.remote

import data.remote.response.forecast.ForecastResponse
import domain.model.location.DeviceLocation

interface RemoteDataSource {
    suspend fun fetchForecast(deviceLocation: DeviceLocation): ForecastResponse
}