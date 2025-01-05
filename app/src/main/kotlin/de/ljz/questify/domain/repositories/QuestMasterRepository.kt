package de.ljz.questify.domain.repositories

import androidx.datastore.core.DataStore
import de.ljz.questify.domain.datastore.Tutorials
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestMasterRepository @Inject constructor(
    private val tutorialsDatastore: DataStore<Tutorials>
) : BaseRepository() {

    fun getQuestMaster(): Flow<Tutorials> {
        return tutorialsDatastore.data
    }

    suspend fun setDashboardOnboardingDone() {
        tutorialsDatastore.updateData {
            it.copy(
                dashboardOnboarding = true
            )
        }
    }

    suspend fun setQuestsOnboardingDone() {
        tutorialsDatastore.updateData {
            it.copy(
                questsOnboarding = true
            )
        }
    }

}