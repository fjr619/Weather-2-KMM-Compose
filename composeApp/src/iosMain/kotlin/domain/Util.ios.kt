package domain

import kotlinx.datetime.LocalDateTime
import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.Platform

import platform.Foundation.NSCalendar
import platform.Foundation.NSDateFormatter

@OptIn(ExperimentalNativeApi::class)
actual val isDebug: Boolean
    get() = Platform.isDebugBinary

actual fun LocalDateTime.format(format: String): String {
    val dateFormatter = NSDateFormatter()
    dateFormatter.dateFormat = format
    return dateFormatter.stringFromDate(
        toNSDate(NSCalendar.currentCalendar)
            ?: throw IllegalStateException("Could not convert kotlin date to NSDate $this")
    )
}