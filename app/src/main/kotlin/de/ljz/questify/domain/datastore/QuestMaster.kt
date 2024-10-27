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
data class QuestMaster(
    @SerialName("dashboard_onboarding_done")
    val dashboardOnboarding: Boolean = false,

    @SerialName("quests_onboarding_done")
    val questsOnboarding: Boolean = false,
)

object QuestMasterSerializer : Serializer<QuestMaster> {
    override val defaultValue: QuestMaster
        get() = QuestMaster()

    val jsonFormat = Json {
        ignoreUnknownKeys = true // Ignore unknown fields for robustness
        isLenient = true // Lenient parsing
        prettyPrint = false // No pretty printing for faster serialization
    }

    override suspend fun readFrom(input: InputStream): QuestMaster {
        return try {
            jsonFormat.decodeFromString(
                deserializer = QuestMaster.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: Exception) {
            Log.e(TAG, "Failed to deserialize QuestMaster: ${e.stackTraceToString()}")
            QuestMaster()
        }
    }

    override suspend fun writeTo(t: QuestMaster, output: OutputStream) {
        try {
            output.write(
                jsonFormat.encodeToString(
                    serializer = QuestMaster.serializer(),
                    value = t
                ).encodeToByteArray()
            )
        } catch (e: Exception) {
            Log.e(TAG, "Failed to serialize QuestMaster: ${e.stackTraceToString()}")
        }
    }

}