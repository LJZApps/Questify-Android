package de.ljz.questify.ui.features.trophies

import de.ljz.questify.domain.models.trophies.TrophyCategoryEntity
import de.ljz.questify.domain.models.trophies.TrophyEntity

data class TrophiesOverviewUiState(
    val trophies: List<TrophyEntity> = emptyList(),
    val trophiesCategoriesUiState: TrophiesCategoriesUiState = TrophiesCategoriesUiState()
)

data class TrophiesCategoriesUiState(
    val categories: List<TrophyCategoryEntity> = emptyList()
)
