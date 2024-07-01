package presentation.route

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CalendarViewWeek
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource
import weathercmp.composeapp.generated.resources.Res
import weathercmp.composeapp.generated.resources.daily
import weathercmp.composeapp.generated.resources.home

//for bottom navigation
sealed class MaterialNavRoute(
    val route: String,
    val resourceTitle: StringResource,
    val icon: ImageVector
) {

    data object Home : MaterialNavRoute(
        route = Route.Home.route,
        resourceTitle = Res.string.home,
        icon = Icons.Filled.Home
    )

    data object Daily : MaterialNavRoute(
        route = Route.Daily.route,
        resourceTitle = Res.string.daily,
        icon = Icons.Filled.CalendarMonth
    )
}