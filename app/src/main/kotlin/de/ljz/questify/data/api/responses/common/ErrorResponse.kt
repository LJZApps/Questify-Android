package de.ljz.questify.data.api.responses.common

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    @SerializedName("error_code")
    val errorCode: String? = "unknown_error",

    @SerializedName("error_message")
    val errorMessage: String? = "An unknown error occurred"
)
