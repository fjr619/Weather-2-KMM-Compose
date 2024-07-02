package data.repository

import data.location.LocationService
import domain.model.location.DeviceLocation
import domain.repository.LocationRepository

class LocationRepositoryImpl(
    private val locationService: LocationService
): LocationRepository {
    override suspend fun getCurrentLocation(): DeviceLocation {
        return locationService.getCurrentLocation()
    }
}