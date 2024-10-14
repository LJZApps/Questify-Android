package de.ljz.questify.data.repositories

import androidx.datastore.core.DataStore
import de.ljz.questify.data.datastore.AppSettings
import de.ljz.questify.ui.state.ThemeBehavior
import kotlinx.coroutines.flow.Flow

class AppSettingsRepository(
    private val appSettingsDataStore: DataStore<AppSettings>
) : BaseRepository() {

    fun getAppSettings(): Flow<AppSettings> {
        return appSettingsDataStore.data
    }

    suspend fun setOnboardingDone() {
        appSettingsDataStore.updateData {
            it.copy(
                setupDone = true
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

}