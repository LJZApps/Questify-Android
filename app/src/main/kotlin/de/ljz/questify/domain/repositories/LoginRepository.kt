package de.ljz.questify.domain.repositories

import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import de.ljz.questify.data.api.core.ApiClient
import de.ljz.questify.data.api.responses.common.ErrorResponse
import de.ljz.questify.data.api.responses.login.LoginResponse
import de.ljz.questify.data.api.responses.register.RegisterResponse
import de.ljz.questify.data.mapper.ErrorResponseMapper
import de.ljz.questify.data.shared_preferences.SessionManager
import de.ljz.questify.util.suspendMessageOnException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(
    private val apiClient: ApiClient,
    private val sessionManager: SessionManager
) : BaseRepository() {

    suspend fun login(
        username: String,
        password: String,
        onSuccess: (suspend (LoginResponse) -> Unit)? = null,
        onError: (suspend (ErrorResponse) -> Unit)? = null
    ) {
        apiClient.loginService.login(username, password)
            .suspendOnSuccess {
                sessionManager.setAccessToken(data.accessToken)

                onSuccess?.invoke(data)
            }
            .suspendOnError(ErrorResponseMapper) {
                onError?.invoke(this)
            }
            .suspendMessageOnException {
                onError?.invoke(it)
            }
    }

    suspend fun register(
        displayName: String,
        username: String,
        biography: String? = null,
        onSuccess: (suspend (RegisterResponse) -> Unit)? = null,
        onError: (suspend (ErrorResponse) -> Unit)? = null
    ) {
        apiClient.loginService.register(displayName, username, biography)
            .suspendOnSuccess {
                sessionManager.setAccessToken(data.accessToken)

                onSuccess?.invoke(data)
            }
            .suspendOnError(ErrorResponseMapper) {
                onError?.invoke(this)
            }
            .suspendMessageOnException {
                onError?.invoke(it)
            }
    }
}