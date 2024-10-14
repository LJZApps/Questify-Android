package de.ljz.questify.data.api.core

import kotlin.random.Random

sealed class NetworkError {
    object NoInternet : NetworkError() {
        override fun equals(other: Any?) = false
        override fun hashCode() = Random.nextInt()
    }

    object InvalidResponse : NetworkError() {
        override fun equals(other: Any?) = false
        override fun hashCode() = Random.nextInt()
    }

    data class RequestFailed(val errorCode: String?, val errorMessage: String?) : NetworkError() {
        override fun equals(other: Any?) = false
        override fun hashCode() = Random.nextInt()
    }

    data class HttpError(val errorCode: String?, val errorMessage: String?) : NetworkError() {
        override fun equals(other: Any?) = false
        override fun hashCode() = Random.nextInt()
    }

    override fun equals(other: Any?) = false
    override fun hashCode() = Random.nextInt()
}