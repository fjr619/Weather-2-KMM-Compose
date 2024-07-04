package di

import data.local.datastore.DataStoreProvider
import data.local.datastore.DataStoreProviderImpl
import data.location.AndroidLocationService
import data.location.LocationService
import dev.icerock.moko.permissions.PermissionsController
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<DataStoreProvider> { DataStoreProviderImpl(androidContext()) }
    factory <LocationService> { AndroidLocationService(context = androidContext()) }
    factory <PermissionsController> { PermissionsController(applicationContext = androidContext()) }
}