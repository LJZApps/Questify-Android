package de.ljz.questify.domain.datastore

import android.util.Log
import androidx.datastore.core.Serializer
import de.ljz.questify.core.application.TAG
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Serializable
data class FirstSetup(
    @SerialName("dashboard_onboarding_done")
    val dashboardOnboarding: Boolean = false,

    @SerialName("quests_onboarding_done")
    val questsOnboarding: Boolean = false,
)

object FirstSetupSerializer : Serializer<FirstSetup> {
    override val defaultValue: FirstSetup
        get() = FirstSetup()

    val jsonFormat = Json {
        ignoreUnknownKeys = true // Ignore unknown fields for robustness
        isLenient = true // Lenient parsing
        prettyPrint = false // No pretty printing for faster serialization
    }

    override suspend fun readFrom(input: InputStream): FirstSetup {
        return try {
            jsonFormat.decodeFromString(
                deserializer = FirstSetup.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: Exception) {
            Log.e(TAG, "Failed to deserialize FirstSetup: ${e.stackTraceToString()}")
            FirstSetup()
        }
    }

    override suspend fun writeTo(t: FirstSetup, output: OutputStream) {
        try {
            output.write(
                jsonFormat.encodeToString(
                    serializer = FirstSetup.serializer(),
                    value = t
                ).encodeToByteArray()
            )
        } catch (e: Exception) {
            Log.e(TAG, "Failed to serialize FirstSetup: ${e.stackTraceToString()}")
        }
    }

}