package de.ljz.questify.ui.features.createquest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.data.database.models.entities.quests.MainQuestEntity
import de.ljz.questify.data.repositories.QuestRepository
import de.ljz.questify.data.shared.Points
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CreateQuestViewModel @Inject constructor(
    private val questRepository: QuestRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(CreateQuestUiState())
    val uiState: StateFlow<CreateQuestUiState> = _uiState.asStateFlow()

    fun createQuest() {
        val quest = MainQuestEntity(
            title = _uiState.value.title,
            description = _uiState.value.description,
            points = Points.EASY,
            createdAt = Date(),
        )

        viewModelScope.launch {
            questRepository.addMainQuest(quest)
        }
    }

    fun updateTitle(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun updateDescription(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun updateDifficulty(difficulty: Int) {
        _uiState.value = _uiState.value.copy(difficulty = difficulty)
    }
}