package de.ljz.questify.core.domain.repositories

import de.ljz.questify.core.utils.SortingDirections
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

open class BaseRepository {

    fun requestBody(value: String, contentType: String = "text/plain"): RequestBody {
        return value.toRequestBody(contentType = contentType.toMediaTypeOrNull())
    }

    fun requestBody(value: Number, contentType: String = "text/plain"): RequestBody {
        return value.toString().toRequestBody(contentType = contentType.toMediaTypeOrNull())
    }

    fun requestBody(name: String, value: File): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            name = name,
            filename = value.name,
            body = value.asRequestBody()
        )
    }

    fun requestBody(value: Date): RequestBody {
        return SimpleDateFormat("yyy-MM-dd", Locale.getDefault()).let {
            try {
                it.format(value)
            } catch (e: Exception) {
                ""
            }
        }.let {
            requestBody(it)
        }
    }

    fun part(name: String, file: File, contentType: String): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            name = name,
            filename = file.name,
            body = file.asRequestBody(
                contentType = contentType.toMediaTypeOrNull()
            )
        )
    }

    data class QuestSortingData(
        val questSortingDirection: SortingDirections,
        val showCompletedQuests: Boolean = false
    )
}