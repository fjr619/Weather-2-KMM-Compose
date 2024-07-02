package presentation.ui.root

import dev.icerock.moko.permissions.PermissionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

data class RootState(
    val isPermissionGranted: Boolean = false,
    val permissionState: PermissionState = PermissionState.NotDetermined,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: String? = null
) {
}

internal fun MutableStateFlow<RootState>.updatePermissionState(permissionState: PermissionState) {
    update {
        it.copy(permissionState = permissionState)
    }
}

internal fun MutableStateFlow<RootState>.updatePermissionGranted(isPermissionGranted: Boolean) {
    update {
        it.copy(isPermissionGranted = isPermissionGranted)
    }
}