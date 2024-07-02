package domain

import fjr619.weathercmp.BuildConfig
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter

actual val isDebug: Boolean
    get() = BuildConfig.DEBUG

actual fun LocalDateTime.format(format: String): String = DateTimeFormatter.ofPattern(format).format(this.toJavaLocalDateTime())