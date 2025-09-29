package de.ljz.questify.feature.settings.data.models

import android.util.Log
import androidx.datastore.core.Serializer
import de.ljz.questify.core.utils.TAG
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Serializable
data class AppSettings(
    @SerialName("onboarding_state")
    val onboardingState: Boolean = false,

    @SerialName("last_opened_version")
    val lastOpenedVersion: Int = 0,

    @SerialName("theme_behavior")
    val themeBehavior: ThemeBehavior = ThemeBehavior.SYSTEM_STANDARD
)

object AppSettingsSerializer : Serializer<AppSettings> {
    override val defaultValue: AppSettings
        get() = AppSettings()

    val jsonFormat = Json {
        ignoreUnknownKeys = true // Ignore unknown fields for robustness
        isLenient = true // Lenient parsing
        prettyPrint = false // No pretty printing for faster serialization
    }

    override suspend fun readFrom(input: InputStream): AppSettings {
        return try {
            jsonFormat.decodeFromString(
                deserializer = AppSettings.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: Exception) {
            Log.e(TAG, "Failed to deserialize AppSettings: ${e.stackTraceToString()}")
            AppSettings()
        }
    }

    override suspend fun writeTo(t: AppSettings, output: OutputStream) {
        try {
            output.write(
                jsonFormat.encodeToString(
                    serializer = AppSettings.serializer(),
                    value = t
                ).encodeToByteArray()
            )
        } catch (e: Exception) {
            Log.e(TAG, "Failed to serialize AppSettings: ${e.stackTraceToString()}")
        }
    }

}