package de.ljz.questify.util

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.SuspensionFunction
import de.ljz.questify.data.api.responses.common.ErrorResponse
import java.net.ConnectException
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
@JvmSynthetic
@SuspensionFunction
public suspend inline fun <T> ApiResponse<T>.suspendMessageOnException(
  crossinline onError: suspend (ErrorResponse) -> Unit,
): ApiResponse<T> {
  contract { callsInPlace(onError, InvocationKind.AT_MOST_ONCE) }
  if (this is ApiResponse.Failure.Exception) {
    onError(
      when (this.throwable) {
        is ConnectException -> {
          ErrorResponse("unknown_error", "The Server is currently unavailable.\nPlease try again later.")
        }
        else -> ErrorResponse("unknown_error", this.throwable.toString())
      }
    )
  }
  return this
}