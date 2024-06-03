package de.ljz.questify.data.api.core

import de.ljz.questify.data.api.services.LoginService
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
class ApiClient(
  retrofit: Retrofit,
) {
  val loginService: LoginService = retrofit.create(LoginService::class.java)
}