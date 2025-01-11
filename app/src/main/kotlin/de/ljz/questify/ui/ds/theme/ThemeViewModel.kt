package de.ljz.questify.ui.ds.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.domain.repositories.AppSettingsRepository
import de.ljz.questify.ui.state.ThemeBehavior
import de.ljz.questify.ui.state.ThemeColor
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository
) : ViewModel() {
    private val _themeBehavior = MutableStateFlow(ThemeBehavior.SYSTEM_STANDARD)
    val themeBehavior: StateFlow<ThemeBehavior> = _themeBehavior

    private val _themeColor = MutableStateFlow(ThemeColor.RED)
    val themeColor: StateFlow<ThemeColor> = _themeColor

    private val _dynamicColorEnabled = MutableStateFlow(false)
    val dynamicColorsEnabled: StateFlow<Boolean> = _dynamicColorEnabled

    init {
        viewModelScope.launch {
            appSettingsRepository.getAppSettings().collectLatest { settings ->
                _themeBehavior.value = settings.themeBehavior
                _themeColor.value = settings.themeColor
                _dynamicColorEnabled.value = settings.dynamicThemeColors
            }
        }
    }
}
