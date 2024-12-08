package io.greedy.goblin.api.v1.ws

import io.greedy.goblin.api.v1.models.Error
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameConnectResponse(
    // Indicates if the request was successful
    @SerialName(value = "success")
    val success: kotlin.Boolean? = null,
    // Optional message with details (e.g., error description)
    @SerialName(value = "message")
    val message: kotlin.String? = null,
    // List of errors that occurred during the request
    @SerialName(value = "errors")
    val errors: kotlin.collections.List<Error>? = null,
)
