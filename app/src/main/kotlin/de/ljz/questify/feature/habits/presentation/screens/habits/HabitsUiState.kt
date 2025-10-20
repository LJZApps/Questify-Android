package de.ljz.questify.feature.habits.presentation.screens.habits

import de.ljz.questify.feature.habits.data.models.HabitsEntity

data class HabitsUiState(
    val isSortingBottomSheetOpen: Boolean,
    val habits: List<HabitsEntity>
)
