package de.ljz.questify.data.api.core

import android.util.Log
import com.squareup.moshi.Moshi
import de.ljz.questify.BuildConfig
import de.ljz.questify.core.application.TAG
import de.ljz.questify.data.api.responses.common.ErrorResponse
import de.ljz.questify.data.api.responses.common.SuccessResponse
import de.ljz.questify.data.api.responses.login.LoginResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object NetworkUtils {

  fun parseSuccessResponse(moshi: Moshi, response: String): SuccessResponse? {
    return try {
      moshi.adapter(SuccessResponse::class.java)?.fromJson(response)
    } catch (e: Exception) {
      null
    }
  }

  fun parseErrorResponse(moshi: Moshi, response: String): ErrorResponse? {
    return try {
      moshi.adapter(ErrorResponse::class.java)?.fromJson(response)
    } catch (e: Exception) {
      null
    }
  }

  suspend fun loginWithRefreshToken(refreshToken: String): LoginResponse? {
    val httpClient = HttpClient(CIO) {
      install(ContentNegotiation) {
        json(
          Json {
            ignoreUnknownKeys = true
          }
        )
      }
    }

    return httpClient.request(
      builder = HttpRequestBuilder().apply {
        method = HttpMethod.Post
        url("${BuildConfig.BASE_URL}/api/oauth/token")
        parameter("refresh_token", refreshToken)
        parameter("grant_type", "refresh_token")
      }
    ).let { response ->
      try {
        response.body<LoginResponse>()
      } catch (e: Exception) {
        Log.e(TAG, "Error while refreshing access token: ${e.stackTraceToString()}\nReceived response: ${response.bodyAsText()}")
        null
      }
    }
  }
}