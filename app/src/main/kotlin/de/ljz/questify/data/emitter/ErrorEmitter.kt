package de.ljz.questify.data.emitter

import androidx.annotation.StringRes
import de.ljz.questify.data.shared.StringWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ErrorEmitter {

    private val _channel = MutableStateFlow<StringWrapper?>(null)
    val channel = _channel.asStateFlow()

    suspend fun emit(value: String) {
        _channel.emit(StringWrapper.Value(value))
    }

    suspend fun emit(@StringRes value: Int) {
        _channel.emit(StringWrapper.Resource(value))
    }

    suspend fun clear() {
        _channel.emit(null)
    }

}