package de.ljz.questify.data.api.responses.register

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.serialization.Serializable

@JsonClass(generateAdapter = true)
@Serializable
data class ValidateEmailResponse (
  @Json(name = "success")
  @SerializedName("success")
  val success: Boolean
)