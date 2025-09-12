package de.ljz.questify.feature.player_stats.presentation.screens.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.feature.player_stats.data.models.PlayerStats
import de.ljz.questify.feature.player_stats.domain.repositories.PlayerStatsRepository
import de.ljz.questify.feature.quests.domain.repositories.QuestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val playerStatsRepository: PlayerStatsRepository,
    private val questRepository: QuestRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        value = StatsUiState(
            isLoading = false,
            playerStats = PlayerStats(),
            questsCompleted = 0,
            xpForNextLevel = 100
        )
    )
    val uiState: StateFlow<StatsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            launch {
                playerStatsRepository.getPlayerStats()
                    .onEach { playerStats ->
                        _uiState.value = _uiState.value.copy(playerStats = playerStats)
                    }
                    .launchIn(viewModelScope)
            }
        }
    }

    fun onUiEvent(event: StatsUiEvent) {
        when (event) {
            else -> Unit
        }
    }
}