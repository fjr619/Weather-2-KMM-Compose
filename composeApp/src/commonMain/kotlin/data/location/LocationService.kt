package data.location

import domain.model.location.DeviceLocation

interface LocationService {
    suspend fun getCurrentLocation(): DeviceLocation
}