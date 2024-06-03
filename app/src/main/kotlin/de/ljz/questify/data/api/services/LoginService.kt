package de.ljz.questify.data.api.services

import com.skydoves.sandwich.ApiResponse
import de.ljz.questify.data.api.responses.login.LoginResponse
import de.ljz.questify.data.api.responses.register.RegisterResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {

  @POST("/api/login")
  suspend fun login(
    @Query("username") username: String,
    @Query("password") password: String,
  ): ApiResponse<LoginResponse>

  @POST("/api/register")
  suspend fun register(
    @Query("display_name") displayName: String,
    @Query("username") username: String,
    @Query("biography") biography: String? = null,
  ): ApiResponse<RegisterResponse>
}