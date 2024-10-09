package de.ljz.questify.data.datastore

import android.util.Log
import androidx.datastore.core.Serializer
import de.ljz.questify.core.application.TAG
import de.ljz.questify.ui.state.ThemeBehavior
import de.ljz.questify.ui.state.ThemeColor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Serializable
data class AppSettings(
  @SerialName("onboarding_done")
  val setupDone: Boolean = false,

  @SerialName("theme_behavior")
  val themeBehavior: ThemeBehavior = ThemeBehavior.SYSTEM_STANDARD,

  @SerialName("dynamic_theme_colors")
  val dynamicThemeColors: Boolean = false,

  @SerialName("theme_color")
  val themeColor: ThemeColor = ThemeColor.RED
)

object AppSettingsSerializer : Serializer<AppSettings> {
  override val defaultValue: AppSettings
    get() = AppSettings()

  override suspend fun readFrom(input: InputStream): AppSettings {
    return try {
      Json.decodeFromString(
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
        Json.encodeToString(
          serializer = AppSettings.serializer(),
          value = t
        ).encodeToByteArray()
      )
    } catch (e: Exception) {
      Log.e(TAG, "Failed to serialize AppSettings: ${e.stackTraceToString()}")
    }
  }

}