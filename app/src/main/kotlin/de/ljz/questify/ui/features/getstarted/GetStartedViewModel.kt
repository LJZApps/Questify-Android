package de.ljz.questify.ui.features.getstarted

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.domain.repositories.AppSettingsRepository
import de.ljz.questify.ui.features.getstarted.subpages.GetStartedUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetStartedViewModel @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(GetStartedUiState())
    val uiState = _uiState.asStateFlow()

    val messages = listOf(
        "Willkommen, edler Suchender! Ich bin der Quest Master, dein weiser Begleiter auf dieser Reise.",
        "Zusammen werden wir deine Aufgaben in Quests verwandeln und verborgene Kräfte entfesseln.",
        "Questify wird deinen Alltag in ein episches Abenteuer verwandeln – voller Prüfungen und Geheimnisse.",
        "Bist du bereit, deinen Pfad zu erleuchten und dein wahres Potenzial zu entfalten?",
    )

    fun onMessageCompleted() {
        viewModelScope.launch {
            val newIndex = _uiState.value.currentIndex + 1
            _uiState.update {
                it.copy(
                    currentIndex = newIndex,
                    currentText = "",
                    showContinueHint = false,
                    showButton = newIndex == messages.lastIndex
                )
            }
        }
    }

    fun setSetupDone() {
        viewModelScope.launch {
            appSettingsRepository.setOnboardingDone()
        }
    }

    fun updateTypingEffect() {
        viewModelScope.launch {
            val currentMessage = messages[_uiState.value.currentIndex]
            var text = ""
            for (char in currentMessage) {
                text += char
                _uiState.update { it.copy(currentText = text) }
                delay(40) // Tipp-Geschwindigkeit
            }
            _uiState.update { it.copy(showContinueHint = _uiState.value.currentIndex != messages.lastIndex) }
        }
    }

}