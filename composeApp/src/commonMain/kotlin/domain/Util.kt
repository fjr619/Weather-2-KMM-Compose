package domain

import androidx.compose.ui.unit.IntSize
import domain.model.forecast.WeatherInfoItem
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import weathercmp.composeapp.generated.resources.Res
import weathercmp.composeapp.generated.resources.clear_sky
import weathercmp.composeapp.generated.resources.drizzle
import weathercmp.composeapp.generated.resources.fog
import weathercmp.composeapp.generated.resources.freezing_drizzle
import weathercmp.composeapp.generated.resources.freezing_rain
import weathercmp.composeapp.generated.resources.mainly_clear
import weathercmp.composeapp.generated.resources.over_cast
import weathercmp.composeapp.generated.resources.rain_heavy
import weathercmp.composeapp.generated.resources.rain_sligh
import weathercmp.composeapp.generated.resources.snow_fall
import weathercmp.composeapp.generated.resources.snow_fall_slight
import weathercmp.composeapp.generated.resources.thunder_storm

expect val isDebug: Boolean

//https://github.com/Kotlin/kotlinx-datetime/issues/211#issuecomment-1285745207
expect fun LocalDateTime.format(format: String): String
object Util {
    private val DIRECTIONS = listOf(
        "North",
        "North East",
        "East",
        "South East",
        "South",
        "South West",
        "West",
        "North West"
    )

    fun getWindDirection(windDirection: Double): String {
        return DIRECTIONS[(windDirection % 360 / 45 % 8).toInt()]
    }

    fun formatUnixDate(pattern: String, time: Long): String {
        val instant = Instant.fromEpochSeconds(time)
        val date = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        return date.format(pattern)
    }

    fun getWeatherInfo(code: Int): WeatherInfoItem {
        return when (code) {
            0 -> WeatherInfoItem("Clear sky", Res.drawable.clear_sky)
            1 -> WeatherInfoItem("Mainly clear", Res.drawable.mainly_clear)
            2 -> WeatherInfoItem("partly cloudy", Res.drawable.mainly_clear)
            3 -> WeatherInfoItem("overcast", Res.drawable.over_cast)
            45, 48 -> WeatherInfoItem("Fog", Res.drawable.fog)
            51, 53, 55,
            -> WeatherInfoItem("Drizzle", Res.drawable.drizzle)

            56, 57 -> WeatherInfoItem("Freezing Drizzle", Res.drawable.freezing_drizzle)
            61,
            -> WeatherInfoItem("Rain: Slight", Res.drawable.rain_sligh)

            63 -> WeatherInfoItem("Rain: Moderate", Res.drawable.rain_heavy)
            65 -> WeatherInfoItem("Rain: Heavy", Res.drawable.rain_heavy)
            66, 67 -> WeatherInfoItem("Freezing Rain", Res.drawable.freezing_rain)
            71 -> WeatherInfoItem("Snow fall: Slight", Res.drawable.snow_fall_slight)
            73 -> WeatherInfoItem("Snow fall: moderate", Res.drawable.snow_fall_slight)
            75 -> WeatherInfoItem("Snow fall: Heavy", Res.drawable.snow_fall)
            77 -> WeatherInfoItem("Snow grains", Res.drawable.snow_fall)
            80, 81, 82 -> WeatherInfoItem("Rain showers: Slight", Res.drawable.rain_sligh)
            85, 86 -> WeatherInfoItem("Snow showers slight", Res.drawable.snow_fall_slight)
            95, 96, 99 -> WeatherInfoItem("Thunderstorm: Slight", Res.drawable.thunder_storm)
            else -> WeatherInfoItem("Unknown", Res.drawable.clear_sky)
        }
    }

    fun isTodayDate(day: String): Boolean {
        val todayDate = formatUnixDate("E", Clock.System.now().epochSeconds)
        return todayDate.lowercase() == day.lowercase()
    }
}