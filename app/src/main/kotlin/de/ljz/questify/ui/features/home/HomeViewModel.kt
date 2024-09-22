package de.ljz.questify.ui.features.home

import cafe.adriel.voyager.core.model.StateScreenModel
import de.ljz.questify.core.coroutine.ContextProvider
import de.ljz.questify.data.sharedpreferences.SessionManager
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class HomeScreenModel @Inject constructor(
  private val contextProvider: ContextProvider,
  private val sessionManager: SessionManager
) : StateScreenModel<HomeUiState>(HomeUiState()) {

  fun showCreateQuestDialog() {
    mutableState.update {
      it.copy(
        createQuestDialogVisible = true
      )
    }
  }

  fun hideCreateQuestDialog() {
    mutableState.update {
      it.copy(
        createQuestDialogVisible = false
      )
    }
  }

}