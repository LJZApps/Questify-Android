package de.ljz.questify.data.api.core

import com.squareup.moshi.Moshi
import de.ljz.questify.data.api.responses.common.ErrorResponse
import de.ljz.questify.data.api.responses.common.SuccessResponse

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

    /*
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
                Log.e(
                    TAG,
                    "Error while refreshing access token: ${e.stackTraceToString()}\nReceived response: ${response.bodyAsText()}"
                )
                null
            }
        }
    }

     */
}