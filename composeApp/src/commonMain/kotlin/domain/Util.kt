package domain

import domain.model.forecast.WeatherInfoItem
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

expect val isDebug: Boolean

//https://github.com/Kotlin/kotlinx-datetime/issues/211#issuecomment-1285745207
expect fun LocalDateTime.format(format: String): String
object Util {
    fun formatUnixDate(pattern: String, time: Long): String {
        val instant = Instant.fromEpochSeconds(time)
        val date = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        return date.format(pattern)
    }
}