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
data class Tutorials(
    @SerialName("tutorials_enabled")
    val tutorialsEnabled: Boolean = true,

    @SerialName("dashboard_tutorial_done")
    val dashboardOnboarding: Boolean = false,

    @SerialName("quests_tutorial_done")
    val questsOnboarding: Boolean = false,

    @SerialName("trophies_tutorial_done")
    val trophiesOnboarding: Boolean = false
)

object QuestMasterSerializer : Serializer<Tutorials> {
    override val defaultValue: Tutorials
        get() = Tutorials()

    val jsonFormat = Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = false
    }

    override suspend fun readFrom(input: InputStream): Tutorials {
        return try {
            jsonFormat.decodeFromString(
                deserializer = Tutorials.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: Exception) {
            Log.e(TAG, "Failed to deserialize Tutorials: ${e.stackTraceToString()}")
            Tutorials()
        }
    }

    override suspend fun writeTo(t: Tutorials, output: OutputStream) {
        try {
            output.write(
                jsonFormat.encodeToString(
                    serializer = Tutorials.serializer(),
                    value = t
                ).encodeToByteArray()
            )
        } catch (e: Exception) {
            Log.e(TAG, "Failed to serialize Tutorials: ${e.stackTraceToString()}")
        }
    }

}