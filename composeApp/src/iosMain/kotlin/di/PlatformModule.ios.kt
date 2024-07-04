package di

import data.location.LocationServiceIOS
import data.location.LocationService
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.ios.PermissionsController as PermissionsControllerIOS
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    factory <LocationService> { LocationServiceIOS() }
    factory <PermissionsController> { PermissionsControllerIOS() }
}