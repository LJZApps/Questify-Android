package de.ljz.questify.core.data.api.responses.common

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SuccessResponse(
    @param:Json(name = "success")
    val success: Boolean? = false,

    @param:Json(name = "error_code")
    val errorCode: String?,

    @param:Json(name = "error_message")
    val errorMessage: String?
)
