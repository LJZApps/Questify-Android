package de.ljz.questify.feature.settings.domain.repositories

import androidx.datastore.core.DataStore
import de.ljz.questify.core.domain.repositories.BaseRepository
import de.ljz.questify.feature.settings.data.models.FeatureSettings
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