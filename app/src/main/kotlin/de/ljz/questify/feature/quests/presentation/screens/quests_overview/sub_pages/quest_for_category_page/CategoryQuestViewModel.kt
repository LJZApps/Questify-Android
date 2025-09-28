package de.ljz.questify.feature.quests.presentation.screens.quests_overview.sub_pages.quest_for_category_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.feature.quests.domain.use_cases.GetAllQuestsForCategoryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = CategoryQuestViewModel.Factory::class)
class CategoryQuestViewModel @AssistedInject constructor(
    @Assisted private val categoryId: Int,
    private val getAllQuestsForCategoryUseCase: GetAllQuestsForCategoryUseCase
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(categoryId: Int): CategoryQuestViewModel
    }

    private val _uiState = MutableStateFlow(CategoryQuestsUiState())
    val uiState: StateFlow<CategoryQuestsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            getAllQuestsForCategoryUseCase.invoke(categoryId).collect { quests ->
                _uiState.update {
                    it.copy(quests = quests, isLoading = false)
                }
            }
        }
    }
}