package de.ljz.questify.feature.player_stats.presentation.screens.stats

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(StatsUiState())
    val uiState: StateFlow<StatsUiState> = _uiState.asStateFlow()

    fun onUiEvent(event: StatsUiEvent) {
        when (event) {
            else -> Unit
        }
    }
}