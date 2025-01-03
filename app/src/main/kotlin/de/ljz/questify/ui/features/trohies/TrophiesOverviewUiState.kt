package de.ljz.questify.ui.features.trohies

import de.ljz.questify.domain.models.trophies.TrophyEntity

data class TrophiesOverviewUiState(
    val trophies: List<TrophyEntity> = listOf()
)
