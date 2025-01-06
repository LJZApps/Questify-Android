package de.ljz.questify.ui.features.trophies

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TrophiesOverviewViewModel @Inject constructor(
): ViewModel() {
    private val _uiState = MutableStateFlow(TrophiesOverviewUiState())
    val uiState: StateFlow<TrophiesOverviewUiState> = _uiState.asStateFlow()
}