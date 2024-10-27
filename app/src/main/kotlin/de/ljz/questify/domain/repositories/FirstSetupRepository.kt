package de.ljz.questify.domain.repositories

import androidx.datastore.core.DataStore
import de.ljz.questify.domain.datastore.FirstSetup
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirstSetupRepository @Inject constructor(
    private val firstSetupDatastore: DataStore<FirstSetup>
) : BaseRepository() {

    fun getFirstSetup(): Flow<FirstSetup> {
        return firstSetupDatastore.data
    }

    suspend fun setDashboardOnboardingDone() {
        firstSetupDatastore.updateData {
            it.copy(
                dashboardOnboarding = true
            )
        }
    }

    suspend fun setQuestsOnboardingDone() {
        firstSetupDatastore.updateData {
            it.copy(
                questsOnboarding = true
            )
        }
    }

}