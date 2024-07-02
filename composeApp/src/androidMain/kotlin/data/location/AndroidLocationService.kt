package data.location

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import domain.model.location.DeviceLocation
import org.koin.core.component.KoinComponent
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal class AndroidLocationService(
    private val context: Context
) : LocationService {

    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): DeviceLocation = suspendCoroutine { continuation ->

        val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 100)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(2000)
            .setMaxUpdateDelayMillis(100)
            .build()

        val locationCallback: LocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
            }
        }

        fusedLocationClient.requestLocationUpdates(request, locationCallback, null)

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                println("-- getCurrentLocation")
                if (location == null) return@addOnSuccessListener
                continuation.resume(
                    DeviceLocation(
                        latitude = location.latitude,
                        longitude = location.longitude,
                    ),
                )
            }.addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }
}