package de.ljz.questify.domain.repositories.app

import androidx.datastore.core.DataStore
import com.materialkolor.PaletteStyle
import de.ljz.questify.domain.datastore.AppSettings
import de.ljz.questify.domain.repositories.BaseRepository
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

    suspend fun resetOnboarding() {
        appSettingsDataStore.updateData {
            it.copy(
                onboardingState = false
            )
        }
    }

    suspend fun setLastOpenedVersion(version: Int) {
        appSettingsDataStore.updateData {
            it.copy(lastOpenedVersion = version)
        }
    }

    suspend fun setDarkModeBehavior(value: ThemeBehavior) {
        appSettingsDataStore.updateData {
            it.copy(
                themeBehavior = value
            )
        }
    }

    suspend fun setAppColor(color: String) {
        appSettingsDataStore.updateData {
            it.copy(
                appColor = color
            )
        }
    }

    suspend fun isAmoledEnabled(enabled: Boolean) {
        appSettingsDataStore.updateData {
            it.copy(
                isAmoled = enabled
            )
        }
    }

    suspend fun setThemeStyle(themeStyle: PaletteStyle) {
        appSettingsDataStore.updateData {
            it.copy(
                themeStyle = themeStyle
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