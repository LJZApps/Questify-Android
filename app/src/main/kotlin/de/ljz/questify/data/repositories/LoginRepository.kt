package de.ljz.questify.data.repositories

import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import de.ljz.questify.data.api.core.ApiClient
import de.ljz.questify.data.api.responses.common.ErrorResponse
import de.ljz.questify.data.api.responses.login.LoginResponse
import de.ljz.questify.data.api.responses.register.RegisterResponse
import de.ljz.questify.data.mapper.ErrorResponseMapper
import de.ljz.questify.data.sharedpreferences.SessionManager
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
        sessionManager.setAccessToken(data.accessToken.token)
        sessionManager.setRefreshToken(data.refreshToken.token)
        sessionManager.setExpirationTime(data.accessToken.exp)

        onSuccess?.invoke(data)
      }
      .suspendOnError(ErrorResponseMapper) {
        onError?.invoke(this)
      }
      .suspendOnException {
        onError?.invoke(ErrorResponse("unknown_error", this.message))
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
        onSuccess?.invoke(data)
      }
      .suspendOnError(ErrorResponseMapper) {
        onError?.invoke(this)
      }
      .suspendOnException {
        onError?.invoke(ErrorResponse("unknown_error", this.message))
      }
  }
}