package de.ljz.questify.domain.datastore

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.datastore.core.Serializer
import com.materialkolor.PaletteStyle
import com.materialkolor.ktx.toHex
import de.ljz.questify.core.application.TAG
import de.ljz.questify.ui.ds.theme.ThemingEngine
import de.ljz.questify.ui.state.ThemeBehavior
import de.ljz.questify.ui.state.ThemeColor
import de.ljz.questify.util.Standard
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Serializable
data class FeatureSettings(
    @SerialName("quest_fast_adding_enabled")
    val questFastAddingEnabled: Boolean = true,
)

object FeatureSettingsSerializer : Serializer<FeatureSettings> {
    override val defaultValue: FeatureSettings
        get() = FeatureSettings()

    val jsonFormat = Json {
        ignoreUnknownKeys = true // Ignore unknown fields for robustness
        isLenient = true // Lenient parsing
        prettyPrint = false // No pretty printing for faster serialization
    }

    override suspend fun readFrom(input: InputStream): FeatureSettings {
        return try {
            jsonFormat.decodeFromString(
                deserializer = FeatureSettings.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: Exception) {
            Log.e(TAG, "Failed to deserialize AppSettings: ${e.stackTraceToString()}")
            FeatureSettings()
        }
    }

    override suspend fun writeTo(t: FeatureSettings, output: OutputStream) {
        try {
            output.write(
                jsonFormat.encodeToString(
                    serializer = FeatureSettings.serializer(),
                    value = t
                ).encodeToByteArray()
            )
        } catch (e: Exception) {
            Log.e(TAG, "Failed to serialize AppSettings: ${e.stackTraceToString()}")
        }
    }

}