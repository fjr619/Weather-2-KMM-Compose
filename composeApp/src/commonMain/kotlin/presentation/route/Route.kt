package presentation.route

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarViewWeek
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource
import weathercmp.composeapp.generated.resources.Res
import weathercmp.composeapp.generated.resources.daily
import weathercmp.composeapp.generated.resources.home

sealed class Route(
    val route: String
) {
    data object Root : Route("root")
    data object Home : Route("home")
    data object Daily : Route("daily")
}

