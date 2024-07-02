package data.remote

import data.remote.model.request.ForecastRequest
import data.remote.model.response.forecast.ForecastResponse
import domain.model.location.DeviceLocation
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get

class RemoteDataSourceImpl(
    private val httpClient: HttpClient
): RemoteDataSource {
    override suspend fun fetchForecast(deviceLocation: DeviceLocation): ForecastResponse {
        return httpClient.get(
            ForecastRequest(
                latitude = deviceLocation.latitude,
                longitude = deviceLocation.longitude
            )
        ).body()
    }
}