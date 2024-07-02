package di

import data.remote.createHttpClient
import data.remote.httpClientEngine
import data.repository.LocationRepositoryImpl
import domain.repository.LocationRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import org.koin.dsl.module

val dataModule = module{
    factory<LocationRepository> { LocationRepositoryImpl(get()) }
    single<HttpClientEngine> { httpClientEngine() }
    single<HttpClient> { createHttpClient(get()) }

}