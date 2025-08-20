package de.ljz.questify.core.utils

import android.util.Log
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.SuspensionFunction
import de.ljz.questify.core.application.TAG
import de.ljz.questify.core.data.api.responses.common.ErrorResponse
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownServiceException
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
                    Log.e(TAG, "suspendMessageOnException: ${this.throwable}")

                    ErrorResponse(
                        "network_error",
                        "The Server is currently unavailable.\nPlease try again later."
                    )
                }

                is SocketTimeoutException -> {
                    Log.e(TAG, "suspendMessageOnException: ${this.throwable}")

                    ErrorResponse(
                        "network_error",
                        "We are having trouble connecting to the Server.\nPlease try again later."
                    )
                }

                is UnknownServiceException -> {
                    Log.e(TAG, "suspendMessageOnException: ${this.throwable}")

                    ErrorResponse(
                        "network_error",
                        "The Server is currently unavailable.\nPlease try again later."
                    )
                }

                else -> {
                    Log.e(TAG, "suspendMessageOnException: ${this.throwable}")
                    ErrorResponse(
                        "unknown_error",
                        "An unknown network error occurred.\n\nError:\n${this.throwable}\n\nPlease try again later."
                    )
                }
            }
        )
    }
    return this
}