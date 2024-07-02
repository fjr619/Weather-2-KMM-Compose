package domain.model.forecast

import org.jetbrains.compose.resources.DrawableResource

data class WeatherInfoItem(
    val info: String,
    val icon: DrawableResource
)