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
data class NewFeatureBadges(
    @SerialName("trophies_sidebar")
    val trophiesSideBar: Boolean = false
)

object NewFeatureBadgesSerializer : Serializer<NewFeatureBadges> {
    override val defaultValue: NewFeatureBadges
        get() = NewFeatureBadges()

    val jsonFormat = Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = false
    }

    override suspend fun readFrom(input: InputStream): NewFeatureBadges {
        return try {
            jsonFormat.decodeFromString(
                deserializer = NewFeatureBadges.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: Exception) {
            Log.e(TAG, "Failed to deserialize NewFeatureBadges: ${e.stackTraceToString()}")
            NewFeatureBadges()
        }
    }

    override suspend fun writeTo(t: NewFeatureBadges, output: OutputStream) {
        try {
            output.write(
                jsonFormat.encodeToString(
                    serializer = NewFeatureBadges.serializer(),
                    value = t
                ).encodeToByteArray()
            )
        } catch (e: Exception) {
            Log.e(TAG, "Failed to serialize NewFeatureBadges: ${e.stackTraceToString()}")
        }
    }

}