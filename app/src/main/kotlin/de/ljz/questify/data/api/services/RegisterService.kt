package de.ljz.questify.data.api.services

import com.skydoves.sandwich.ApiResponse
import de.ljz.questify.data.api.responses.register.ValidateEmailResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface RegisterService {

  @POST("/api/oauth/validate-email")
  suspend fun validateEmail(
    @Query("email") email: String
  ): ApiResponse<ValidateEmailResponse>
}