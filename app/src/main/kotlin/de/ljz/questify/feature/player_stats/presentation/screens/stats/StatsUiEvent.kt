package de.ljz.questify.feature.player_stats.presentation.screens.stats

sealed class StatsUiEvent {
    data object NavigateUp : StatsUiEvent()

    data object ToggleDrawer : StatsUiEvent()
}