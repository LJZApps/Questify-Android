package de.ljz.questify.data.api.responses.register

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.serialization.Serializable

@JsonClass(generateAdapter = true)
@Serializable
data class RegisterResponse(
    @Json(name = "success")
    val success: Boolean,

    @Json(name = "access_token")
    val accessToken: String,
)