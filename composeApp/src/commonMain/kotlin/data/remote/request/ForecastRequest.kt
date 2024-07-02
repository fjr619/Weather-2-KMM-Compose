package data.remote.request

import io.ktor.resources.Resource
import kotlinx.serialization.SerialName

@Resource("forecast")
class ForecastRequest(
    @SerialName("latitude") val latitude: Double,
    @SerialName("longitude") val longitude: Double,
    @SerialName("daily") val daily: String = arrayOf(
        "weather_code",
        "temperature_2m_max",
        "temperature_2m_min",
        "wind_speed_10m_max",
        "wind_direction_10m_dominant",
        "sunrise",
        "sunset",
        "uv_index_max",
    ).joinToString(separator = ","),
    @SerialName("current") val current: String = arrayOf(
        "temperature_2m",
        "is_day",
        "weather_code",
        "wind_speed_10m",
        "wind_direction_10m",
    ).joinToString(separator = ","),
    @SerialName("hourly") val hourly: String = arrayOf(
        "weather_code",
        "temperature_2m",
    ).joinToString(separator = ","),
    @SerialName("timeformat") val timeformat: String = "unixtime",
    @SerialName("timezone") val timezone: String = "auto"

)