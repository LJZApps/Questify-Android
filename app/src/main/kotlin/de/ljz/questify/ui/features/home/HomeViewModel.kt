package de.ljz.questify.ui.features.home

import androidx.lifecycle.ViewModel
import cafe.adriel.voyager.core.model.StateScreenModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.core.coroutine.ContextProvider
import de.ljz.questify.data.sharedpreferences.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeScreenModel @Inject constructor(
  private val contextProvider: ContextProvider,
  private val sessionManager: SessionManager
) : ViewModel() {
  private val _uiState = MutableStateFlow(HomeUiState())
  val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

  fun showCreateQuestDialog() {
    _uiState.update {
      it.copy(
        createQuestDialogVisible = true
      )
    }
  }

  fun hideCreateQuestDialog() {
    _uiState.update {
      it.copy(
        createQuestDialogVisible = false
      )
    }
  }

  fun increaseQuestCount() {
    _uiState.update { it.copy(questItemCount = it.questItemCount + 1) }
  }

}