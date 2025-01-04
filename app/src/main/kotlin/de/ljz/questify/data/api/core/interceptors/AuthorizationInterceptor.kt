package de.ljz.questify.data.api.core.interceptors

import de.ljz.questify.data.api.core.NetworkUtils
import de.ljz.questify.data.api.core.annotations.Authenticated
import de.ljz.questify.data.shared_preferences.SessionManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation

class AuthorizationInterceptor(
    private val sessionManager: SessionManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request().let { request ->
            request.tag(Invocation::class.java)?.let { invocation ->
                invocation.method().getAnnotation(Authenticated::class.java)?.let {
                    // Determine if we need to add an access token to the request headers
                    val addAccessToken =
                        (it.optional && sessionManager.isAccessTokenPresent()) || !it.optional

                    if (addAccessToken) {
                        runBlocking {
                            NetworkUtils.loginWithRefreshToken(sessionManager.getAccessToken())
                                ?.let { loginResponse ->
                                    sessionManager.setAccessToken(loginResponse.accessToken)
                                } ?: chain.proceed(request)
                        }
                        // Add the authorization header to the request
                        request.newBuilder().apply {
                            addHeader("Authorization", sessionManager.getAccessToken())
                        }.let { updatedRequest ->
                            chain.proceed(updatedRequest.build())
                        }
                    } else {
                        chain.proceed(request)
                    }
                } ?: chain.proceed(chain.request())
            } ?: chain.proceed(chain.request())
        }
    }
}