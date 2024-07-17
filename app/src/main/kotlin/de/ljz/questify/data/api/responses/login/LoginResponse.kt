package de.ljz.questify.data.api.responses.login

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(
  @Json(name = "success")
  val success: Boolean,

  @Json(name = "access_token")
  val accessToken: String,
)