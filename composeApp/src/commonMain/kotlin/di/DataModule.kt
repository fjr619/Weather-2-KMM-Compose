package di

import data.local.datastore.PreferencesDataSource
import data.local.datastore.PreferencesDataSourceImpl
import data.mapper.to_domain.CurrentWeatherMapper
import data.mapper.to_domain.DailyMapper
import data.mapper.to_domain.HourlyMapper
import data.mapper.to_domain.MapperToDomain
import data.mapper.to_domain.WeatherMapper
import data.model.DailyWeather
import data.model.HourlyWeather
import data.remote.RemoteDataSource
import data.remote.RemoteDataSourceImpl
import data.remote.createHttpClient
import data.remote.httpClientEngine
import data.remote.response.forecast.ForecastResponse
import data.repository.ForecastRepositoryImpl
import data.repository.LocationRepositoryImpl
import data.repository.ThemeRepositoryImpl
import domain.model.forecast.CurrentWeather
import domain.model.forecast.Daily
import domain.model.forecast.Hourly
import domain.model.forecast.Weather
import domain.repository.ForecastRepository
import domain.repository.LocationRepository
import domain.repository.ThemeRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module{
    single<HttpClientEngine> { httpClientEngine() }
    single<HttpClient> { createHttpClient(get()) }
    single<RemoteDataSource> { RemoteDataSourceImpl(get()) }
    single<PreferencesDataSource> { PreferencesDataSourceImpl(get()) }
    factory<ForecastRepository>{ ForecastRepositoryImpl(get(), get(named("WeatherMapper"))) }
    factory<LocationRepository> { LocationRepositoryImpl(get()) }
    factory<ThemeRepository> { ThemeRepositoryImpl(get()) }
}

val mapperModule = module {
    factory<MapperToDomain<Daily, DailyWeather>>(named("DailyMapper")) {
        DailyMapper()
    }

    factory<MapperToDomain<CurrentWeather, data.model.CurrentWeather>>(named("CurrentWeatherMapper")) {
        CurrentWeatherMapper()
    }

    factory<MapperToDomain<Hourly, HourlyWeather>>(named("HourlyMapper")) {
        HourlyMapper()
    }
    factory<MapperToDomain<Weather,ForecastResponse>>(named("WeatherMapper")) {
        WeatherMapper(
            get(named("DailyMapper")),
            get(named("CurrentWeatherMapper")),
            get(named("HourlyMapper"))
        )
    }
}