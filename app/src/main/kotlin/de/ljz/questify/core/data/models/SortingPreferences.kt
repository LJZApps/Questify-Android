package de.ljz.questify.core.data.models

import android.util.Log
import androidx.datastore.core.Serializer
import de.ljz.questify.core.utils.QuestSorting
import de.ljz.questify.core.utils.SortingDirections
import de.ljz.questify.core.utils.TAG
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Serializable
data class SortingPreferences(
    @SerialName("quest_sorting")
    val questSorting: QuestSorting = QuestSorting.ID,

    @SerialName("quest_sorting_direction")
    val questSortingDirection: SortingDirections = SortingDirections.DESCENDING,

    @SerialName("show_completed_quests")
    val showCompletedQuests: Boolean = false
)

object SortingPreferencesSerializer : Serializer<SortingPreferences> {
    override val defaultValue: SortingPreferences
        get() = SortingPreferences()

    val jsonFormat = Json {
        ignoreUnknownKeys = true // Ignore unknown fields for robustness
        isLenient = true // Lenient parsing
        prettyPrint = false // No pretty printing for faster serialization
    }

    override suspend fun readFrom(input: InputStream): SortingPreferences {
        return try {
            jsonFormat.decodeFromString(
                deserializer = SortingPreferences.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: Exception) {
            Log.e(TAG, "Failed to deserialize AppSettings: ${e.stackTraceToString()}")
            SortingPreferences()
        }
    }

    override suspend fun writeTo(t: SortingPreferences, output: OutputStream) {
        try {
            output.write(
                jsonFormat.encodeToString(
                    serializer = SortingPreferences.serializer(),
                    value = t
                ).encodeToByteArray()
            )
        } catch (e: Exception) {
            Log.e(TAG, "Failed to serialize AppSettings: ${e.stackTraceToString()}")
        }
    }

}