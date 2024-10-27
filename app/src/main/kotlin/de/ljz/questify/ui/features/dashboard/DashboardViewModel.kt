package de.ljz.questify.ui.features.dashboard

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.domain.repositories.AppUserRepository
import de.ljz.questify.domain.repositories.QuestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val appUserRepository: AppUserRepository,
    private val questRepository: QuestRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState = _uiState.asStateFlow()

    init {

    }
}