package data.remote.response.forecast

import data.model.CurrentUnits
import data.model.CurrentWeather
import data.model.DailyUnits
import data.model.DailyWeather
import data.model.HourlyUnits
import data.model.HourlyWeather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastResponse(
    @SerialName("current")
    val current: CurrentWeather,
    @SerialName("current_units")
    val currentUnits: CurrentUnits,
    @SerialName("daily")
    val daily: DailyWeather,
    @SerialName("daily_units")
    val dailyUnits: DailyUnits,
    @SerialName("elevation")
    val elevation: Double,
    @SerialName("generationtime_ms")
    val generationtimeMs: Double,
    @SerialName("hourly")
    val hourly: HourlyWeather,
    @SerialName("hourly_units")
    val hourlyUnits: HourlyUnits,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("timezone")
    val timezone: String,
    @SerialName("timezone_abbreviation")
    val timezoneAbbreviation: String,
    @SerialName("utc_offset_seconds")
    val utcOffsetSeconds: Int
)