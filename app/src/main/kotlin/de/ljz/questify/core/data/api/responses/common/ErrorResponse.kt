package de.ljz.questify.core.data.api.responses.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    @SerialName("error_code")
    val errorCode: String? = "unknown_error",

    @SerialName("error_message")
    val errorMessage: String? = "An unknown error occurred"
)
