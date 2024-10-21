package de.ljz.questify.ui.features.quests.questdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.data.repositories.QuestRepository
import de.ljz.questify.ui.features.quests.questdetail.navigation.QuestDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestDetailViewModel @Inject constructor(
    private val questRepository: QuestRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState = MutableStateFlow(QuestDetailUiState())
    val uiState = _uiState.asStateFlow()

    private val questDetailRoute = savedStateHandle.toRoute<QuestDetail>()
    val questId = questDetailRoute.id

    init {
        viewModelScope.launch {
            val quest = questRepository.findMainQuestById(questId)
            quest.collectLatest { questEntity ->
                _uiState.value = _uiState.value.copy(quest = questEntity)
            }
        }
    }
}