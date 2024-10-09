package de.ljz.questify.data.repositories

import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import de.ljz.questify.data.api.core.ApiClient
import de.ljz.questify.data.api.responses.common.ErrorResponse
import de.ljz.questify.data.api.responses.register.ValidateEmailResponse
import de.ljz.questify.data.mapper.ErrorResponseMapper
import de.ljz.questify.util.suspendMessageOnException

class RegisterRepository(
  private val apiClient: ApiClient
) : BaseRepository() {
  suspend fun validateEmail(
    email: String,
    onSuccess: (suspend (ValidateEmailResponse) -> Unit)? = null,
    onError: (suspend (ErrorResponse) -> Unit)? = null
  ) {
    apiClient.registerService.validateEmail(email)
      .suspendOnSuccess {
        onSuccess?.invoke(data)
      }
      .suspendOnError (ErrorResponseMapper) {
        onError?.invoke(this)
      }
      .suspendMessageOnException {
        onError?.invoke(it)
      }
  }
}