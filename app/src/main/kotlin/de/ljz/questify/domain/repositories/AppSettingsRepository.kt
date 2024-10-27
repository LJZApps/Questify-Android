package de.ljz.questify.domain.repositories

import androidx.datastore.core.DataStore
import de.ljz.questify.data.datastore.AppSettings
import de.ljz.questify.ui.state.ThemeBehavior
import de.ljz.questify.ui.state.ThemeColor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSettingsRepository @Inject constructor(
    private val appSettingsDataStore: DataStore<AppSettings>
) : BaseRepository() {

    fun getAppSettings(): Flow<AppSettings> {
        return appSettingsDataStore.data
    }

    suspend fun setOnboardingDone() {
        appSettingsDataStore.updateData {
            it.copy(
                onboardingState = true
            )
        }
    }

    suspend fun setDarkModeBehavior(value: ThemeBehavior) {
        appSettingsDataStore.updateData {
            it.copy(
                themeBehavior = value
            )
        }
    }

    suspend fun setDynamicColorsEnabled(enabled: Boolean) {
        appSettingsDataStore.updateData {
            it.copy(
                dynamicThemeColors = enabled
            )
        }
    }

    suspend fun setCustomColor(color: ThemeColor) {
        appSettingsDataStore.updateData {
            it.copy(
                themeColor = color
            )
        }
    }

}