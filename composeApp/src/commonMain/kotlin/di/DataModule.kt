package di

import data.repository.LocationRepositoryImpl
import domain.repository.LocationRepository
import org.koin.dsl.module

val dataModule = module{
    factory<LocationRepository> { LocationRepositoryImpl(get()) }
}