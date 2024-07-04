package di

import data.local.datastore.DataStoreProvider
import data.local.datastore.DataStoreProviderImpl
import data.location.LocationServiceIOS
import data.location.LocationService
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.ios.PermissionsController as PermissionsControllerIOS
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<DataStoreProvider> { DataStoreProviderImpl() }
    factory <LocationService> { LocationServiceIOS() }
    factory <PermissionsController> { PermissionsControllerIOS() }
}