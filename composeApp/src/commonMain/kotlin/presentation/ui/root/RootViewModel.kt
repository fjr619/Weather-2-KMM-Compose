package presentation.ui.root

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RootViewModel(
    val permissionsController: PermissionsController,
) : ViewModel() {
    private val _permissionState = MutableStateFlow(PermissionState.NotDetermined)
    val permissionState = _permissionState.asStateFlow()

    private val _permissionGranted = MutableStateFlow(false)
    val permissionGranted = _permissionGranted.asStateFlow()
    fun checkPermissionStatus() {
        viewModelScope.launch {
            _permissionGranted.update {
                permissionsController.isPermissionGranted(permission)
            }
        }
    }

    private val permission = Permission.LOCATION
    fun requestPermission() {
        viewModelScope.launch {
            try {
                checkPermissionStatus()
                permissionsController.providePermission(permission)
                _permissionState.update { PermissionState.Granted }
            } catch (deniedAlwaysException: DeniedAlwaysException) {
                _permissionState.update { PermissionState.DeniedAlways }
                permissionsController.openAppSettings()
            } catch (deniedException: DeniedException) {
                _permissionState.update { PermissionState.Denied }
                requestPermission()
            }
        }
    }

}