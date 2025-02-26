package de.ljz.questify.domain.repositories.app

import androidx.datastore.core.DataStore
import com.materialkolor.PaletteStyle
import de.ljz.questify.domain.datastore.AppSettings
import de.ljz.questify.domain.datastore.FeatureSettings
import de.ljz.questify.domain.repositories.BaseRepository
import de.ljz.questify.ui.state.ThemeBehavior
import de.ljz.questify.ui.state.ThemeColor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeatureSettingsRepository @Inject constructor(
    private val featureSettingsDataStore: DataStore<FeatureSettings>
) : BaseRepository() {

    fun getFeatureSettings(): Flow<FeatureSettings> {
        return featureSettingsDataStore.data
    }

    suspend fun setQuestFastAddingEnabled(enabled: Boolean) {
        featureSettingsDataStore.updateData {
            it.copy(
                questFastAddingEnabled = enabled
            )
        }
    }
}