package de.ljz.questify.core.domain.use_cases

import de.ljz.questify.feature.settings.domain.repositories.AppSettingsRepository
import javax.inject.Inject


class SetOnboardingDoneUseCase @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository
) {
    suspend operator fun invoke() {
        appSettingsRepository.setOnboardingDone()
    }
}