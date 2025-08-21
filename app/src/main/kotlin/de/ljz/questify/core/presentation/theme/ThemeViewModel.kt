package de.ljz.questify.core.presentation.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.feature.settings.domain.repositories.AppSettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    appSettingsRepository: AppSettingsRepository
) : ViewModel() {
    val uiState: StateFlow<ThemeUiState> = appSettingsRepository.getAppSettings()
        .map { settings ->
            // Wandelt das Einstellungs-Objekt direkt in den UI-State um
            ThemeUiState(
                themeBehavior = settings.themeBehavior,
                themeColor = settings.themeColor,
                dynamicColorsEnabled = settings.dynamicThemeColors,
                themingEngine = settings.themingEngine,
                isAmoled = settings.isAmoled,
                themeStyle = settings.themeStyle,
                appColor = settings.appColor
            )
        }
        .stateIn(
            scope = viewModelScope,
            // Der Flow ist nur aktiv, wenn die UI ihn wirklich braucht
            started = SharingStarted.WhileSubscribed(5000),
            // Startwert, bevor der erste Wert vom Flow ankommt
            initialValue = ThemeUiState()
        )
}
