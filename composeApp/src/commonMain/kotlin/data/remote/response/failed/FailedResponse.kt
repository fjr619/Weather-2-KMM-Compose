package data.remote.response.failed

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class FailedResponse(
    @SerialName("reason") val reason: String,
    @SerialName("error") val error: Boolean
)