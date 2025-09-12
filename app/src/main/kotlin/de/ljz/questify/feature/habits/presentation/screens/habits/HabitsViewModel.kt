package de.ljz.questify.feature.habits.presentation.screens.habits

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HabitsViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(
        value = HabitsUiState(
            isSortingBottomSheetOpen = false
        )
    )
    val uiState = _uiState.asStateFlow()
}