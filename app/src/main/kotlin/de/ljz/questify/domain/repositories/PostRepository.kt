package de.ljz.questify.domain.repositories

import de.ljz.questify.data.api.core.ApiClient
import de.ljz.questify.data.api.responses.login.LoginResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepository @Inject constructor(
    private val apiClient: ApiClient
) : BaseRepository() {

    suspend fun getPosts(
        onSuccess: (suspend (LoginResponse) -> Unit)? = null,
        onError: (suspend (Exception) -> Unit)? = null
    ) {

    }
}