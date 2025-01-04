package de.ljz.questify.ui.features.trohies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrophiesOverviewViewModel @Inject constructor(

): ViewModel() {
    private val _uiState = MutableStateFlow(TrophiesOverviewUiState())
    val uiState: StateFlow<TrophiesOverviewUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            // TODO get all user trophies
        }
    }
}