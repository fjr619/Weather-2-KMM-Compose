package presentation.ui.root

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.PermissionsController
import domain.Util
import domain.model.Response
import domain.repository.ForecastRepository
import domain.repository.LocationRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RootViewModel(
    val permissionsController: PermissionsController,
    private val locationRepository: LocationRepository,
    private val forecastRepository: ForecastRepository
) : ViewModel() {

    private val _rootState = MutableStateFlow(RootState())
    val rootState = _rootState.asStateFlow()

    private val permissionLocation = Permission.LOCATION

    fun onEvent(event: RootEvent) {
        when (event) {
            is RootEvent.onRequestPermission -> requestPermission()
            is RootEvent.onCheckPermission -> checkPermissionStatus()
        }
    }

    private fun checkPermissionStatus() {
        viewModelScope.launch {
            _rootState.updatePermissionGranted(
                permissionsController.isPermissionGranted(
                    permissionLocation
                )
            )

            if (permissionsController.isPermissionGranted(permissionLocation) && rootState.value.weatherData == null) {
                _rootState.update {
                    it.copy(
                        isLoading = true,
                    )
                }

                forecastRepository.getForecastWeather(locationRepository.getCurrentLocation())
                    .collect { response ->
                        when (response) {
                            is Response.Success -> {
                                _rootState.update {
                                    it.copy(
                                        isLoading = false,
                                        weatherData = response.data,
                                    )
                                }
                            }

                            is Response.Error -> {
                                _rootState.update {
                                    it.copy(
                                        isLoading = false,
                                        isError = true,
                                        error = response.message
                                    )
                                }
                            }
                        }
                    }
            }
        }
    }

    private fun requestPermission() {
        viewModelScope.launch {
            try {
                permissionsController.providePermission(permissionLocation)
                _rootState.updatePermissionState(PermissionState.Granted)
            } catch (deniedAlwaysException: DeniedAlwaysException) {
                _rootState.updatePermissionState(PermissionState.DeniedAlways)
                permissionsController.openAppSettings()
            } catch (deniedException: DeniedException) {
                _rootState.updatePermissionState(PermissionState.Denied)
                requestPermission()
            }
        }
    }
}