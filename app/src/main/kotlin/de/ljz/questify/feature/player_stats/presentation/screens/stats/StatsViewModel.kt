package de.ljz.questify.feature.player_stats.presentation.screens.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.core.utils.calculateXpForNextLevel
import de.ljz.questify.feature.player_stats.data.models.PlayerStats
import de.ljz.questify.feature.player_stats.domain.repositories.PlayerStatsRepository
import de.ljz.questify.feature.quests.domain.repositories.QuestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val playerStatsRepository: PlayerStatsRepository,
    private val questRepository: QuestRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        value = StatsUiState(
            playerStats = PlayerStats(),
            questsCompleted = 0,
            xpForNextLevel = 0
        )
    )
    val uiState: StateFlow<StatsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            launch {
                playerStatsRepository.getPlayerStats().collectLatest { playerStats ->
                    _uiState.update {
                        it.copy(
                            playerStats = playerStats,
                            xpForNextLevel = calculateXpForNextLevel(playerStats.level)
                        )
                    }
                }
            }
        }
    }

    fun onUiEvent(event: StatsUiEvent) {
        when (event) {
            else -> Unit
        }
    }
}