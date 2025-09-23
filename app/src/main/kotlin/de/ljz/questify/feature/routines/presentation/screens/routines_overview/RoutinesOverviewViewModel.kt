package de.ljz.questify.feature.routines.presentation.screens.routines_overview

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RoutinesOverviewViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(
        value = RoutinesOverviewUiState(
            isLoading = false
        )
    )
    val uiState = _uiState.asStateFlow()

    fun onUiEvent(event: RoutinesOverviewUiEvent) {
        when (event) {
            else -> Unit
        }
    }
}