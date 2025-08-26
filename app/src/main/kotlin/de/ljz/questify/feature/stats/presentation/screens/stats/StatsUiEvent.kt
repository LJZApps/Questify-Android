package de.ljz.questify.feature.stats.presentation.screens.stats

sealed class StatsUiEvent {
    data object NavigateUp : StatsUiEvent()

    data object ToggleDrawer : StatsUiEvent()
}