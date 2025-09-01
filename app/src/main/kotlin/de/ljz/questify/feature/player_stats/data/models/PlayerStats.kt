package de.ljz.questify.feature.player_stats.data.models

import android.util.Log
import androidx.datastore.core.Serializer
import de.ljz.questify.core.utils.TAG
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Serializable
enum class PlayerStatus {
    NORMAL,
    EXHAUSTED
}

@Serializable
data class PlayerStats(
    @SerialName("id")
    val id: Int = 1,

    @SerialName("level")
    val level: Int = 1,

    @SerialName("xp")
    val xp: Int = 0,

    @SerialName("points")
    val points: Int = 0,

    @SerialName("current_hp")
    val currentHP: Int = 100,

    @SerialName("max_hp")
    val maxHP: Int = 100,

    @SerialName("status")
    val status: PlayerStatus = PlayerStatus.NORMAL,

    @SerialName("status_expiry_timestamp")
    val statusExpiryTimestamp: Long? = null
)

object PlayerStatsSerializer : Serializer<PlayerStats> {
    override val defaultValue: PlayerStats
        get() = PlayerStats()

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun readFrom(input: InputStream): PlayerStats {
        return try {
            json.decodeFromString(
                deserializer = PlayerStats.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: Exception) {
            Log.e(TAG, "Failed to deserialize PlayerStats: $e")
            defaultValue
        }
    }

    override suspend fun writeTo(t: PlayerStats, output: OutputStream) {
        output.write(
            json.encodeToString(
                serializer = PlayerStats.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}