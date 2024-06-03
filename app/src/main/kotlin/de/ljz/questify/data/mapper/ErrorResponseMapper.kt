package de.ljz.questify.data.mapper

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.mappers.ApiErrorModelMapper
import com.skydoves.sandwich.retrofit.errorBody
import com.skydoves.sandwich.retrofit.statusCode
import de.ljz.questify.data.api.responses.common.ErrorResponse
import org.json.JSONObject

object ErrorResponseMapper : ApiErrorModelMapper<ErrorResponse> {

  override fun map(apiErrorResponse: ApiResponse.Failure.Error): ErrorResponse {
    return when (apiErrorResponse.statusCode.code) {
      404 -> ErrorResponse("NOT_FOUND", "This api call leads to nothing.\nPlease try again later.")
      else -> {
        val body = apiErrorResponse.errorBody?.string() // Konvertiere den errorBody zu einem String
        if (body != null) {
          try {
            // Parse den JSON-String und extrahiere die gewünschten Parameter
            val jsonObject = JSONObject(body)
            val errorCode = jsonObject.optString("error_code")
            val errorMessage = jsonObject.optString("error_message")
            ErrorResponse(errorCode, errorMessage)
          } catch (e: Exception) {
            ErrorResponse("UNKNOWN_ERROR", e.localizedMessage)
          }
        } else {
          ErrorResponse("UNKNOWN_ERROR", "Unknown error occurred.")
        }
      }
    }
  }
}