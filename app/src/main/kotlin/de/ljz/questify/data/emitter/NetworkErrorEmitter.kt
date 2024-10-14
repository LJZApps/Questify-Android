package de.ljz.questify.data.emitter

import de.ljz.questify.data.api.core.NetworkError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NetworkErrorEmitter {

    private val _channel = MutableStateFlow<NetworkError?>(null)
    val channel = _channel.asStateFlow()

    suspend fun emitNoInternet() {
        _channel.emit(NetworkError.NoInternet)
    }

    suspend fun emitInvalidResponse() {
        _channel.emit(NetworkError.InvalidResponse)
    }

    suspend fun emitRequestFailed(errorCode: String?, errorMessage: String?) {
        _channel.emit(
            NetworkError.RequestFailed(
                errorCode = errorCode,
                errorMessage = errorMessage
            )
        )
    }

    suspend fun emitHttpError(errorCode: String?, errorMessage: String?) {
        _channel.emit(NetworkError.HttpError(errorCode, errorMessage))
    }

}