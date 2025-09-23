package de.ljz.questify.feature.quests.presentation.screens.quests_overview

sealed interface QuestOverviewUiEffect {
    data class ShowSnackbar(val message: String) : QuestOverviewUiEffect
}