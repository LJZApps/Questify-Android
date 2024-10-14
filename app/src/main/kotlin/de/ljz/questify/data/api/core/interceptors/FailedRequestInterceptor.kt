package de.ljz.questify.data.api.core.interceptors

import com.squareup.moshi.Moshi
import de.ljz.questify.data.api.core.NetworkUtils
import de.ljz.questify.data.api.core.exceptions.RequestFailedException
import okhttp3.Interceptor
import okhttp3.Response

class FailedRequestInterceptor(private val moshi: Moshi) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        val responseBody = response.peekBody(Long.MAX_VALUE).string()

        /*
         * Only validate:
         * - POST, PUT, PATCH and DELETE-Requests
         * - Requests which have nothing to do with authentication
         */
        if (request.method.uppercase() != "GET" && response.isSuccessful && !request.url.toString()
                .endsWith("/oauth/token")
        ) {
            NetworkUtils.parseSuccessResponse(moshi, responseBody)?.let { successResponse ->
                if (successResponse.success != true) {
                    throw RequestFailedException(
                        errorCode = successResponse.errorCode,
                        errorMessage = successResponse.errorMessage
                    )
                }
            } ?: response
        }

        return response
    }
}