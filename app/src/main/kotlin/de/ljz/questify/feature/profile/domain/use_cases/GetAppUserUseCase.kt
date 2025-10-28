package de.ljz.questify.feature.profile.domain.use_cases

import de.ljz.questify.feature.profile.data.models.AppUser
import de.ljz.questify.feature.profile.domain.repositories.AppUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAppUserUseCase @Inject constructor(
    private val appUserRepository: AppUserRepository
) {
    suspend operator fun invoke(): Flow<AppUser> {
        return appUserRepository.getAppUser()
    }
}