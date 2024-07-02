package data.repository

import data.mapper.to_domain.MapperToDomain
import data.remote.RemoteDataSource
import data.remote.response.failed.ResponseException
import data.remote.response.forecast.ForecastResponse
import domain.model.Response
import domain.model.forecast.Weather
import domain.model.location.DeviceLocation
import domain.repository.ForecastRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ForecastRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val weatherMapper: MapperToDomain<Weather, ForecastResponse>,
) : ForecastRepository {
    override fun getForecastWeather(deviceLocation: DeviceLocation): Flow<Response<Weather>> =
        flow {
            try {
                val response = remoteDataSource.fetchForecast(deviceLocation)
                val weather = weatherMapper.mapToDomain(response)
                emit(Response.Success(weather))
            } catch (e: ResponseException) {
                emit(Response.Error(e.message))
            }
        }.flowOn(Dispatchers.IO)
}