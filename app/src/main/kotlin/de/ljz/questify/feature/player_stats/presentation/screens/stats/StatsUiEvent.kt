package de.ljz.questify.feature.player_stats.presentation.screens.stats

sealed interface StatsUiEvent {
    data object NavigateUp : StatsUiEvent

    data object ToggleDrawer : StatsUiEvent
}