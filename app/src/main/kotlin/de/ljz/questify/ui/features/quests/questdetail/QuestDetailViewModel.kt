package de.ljz.questify.ui.features.quests.questdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.domain.models.quests.MainQuestEntity
import de.ljz.questify.domain.repositories.QuestRepository
import de.ljz.questify.ui.features.quests.questdetail.navigation.QuestDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
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
            questRepository.findMainQuestById(questId).collect { quest ->
                _uiState.value = _uiState.value.copy(
                    questId = quest.id,
                    title = quest.title,
                    description = quest.description ?: ""
                )
            }
        }
    }

    fun updateTitle(newTitle: String) {
        _uiState.value = _uiState.value.copy(title = newTitle)
    }

    fun updateDescription(newDescription: String) {
        _uiState.value = _uiState.value.copy(description = newDescription)
    }
}