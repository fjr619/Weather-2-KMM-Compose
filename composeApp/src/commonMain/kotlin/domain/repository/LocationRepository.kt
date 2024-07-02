package domain.repository

import domain.model.location.DeviceLocation

interface LocationRepository {
    suspend fun getCurrentLocation(): DeviceLocation
}