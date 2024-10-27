package de.ljz.questify.domain.repositories

import androidx.datastore.core.DataStore
import de.ljz.questify.domain.datastore.QuestMaster
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestMasterRepository @Inject constructor(
    private val questMasterDatastore: DataStore<QuestMaster>
) : BaseRepository() {

    fun getQuestMaster(): Flow<QuestMaster> {
        return questMasterDatastore.data
    }

    suspend fun setDashboardOnboardingDone() {
        questMasterDatastore.updateData {
            it.copy(
                dashboardOnboarding = true
            )
        }
    }

    suspend fun setQuestsOnboardingDone() {
        questMasterDatastore.updateData {
            it.copy(
                questsOnboarding = true
            )
        }
    }

}