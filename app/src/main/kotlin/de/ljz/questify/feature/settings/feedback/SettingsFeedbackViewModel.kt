package de.ljz.questify.feature.settings.feedback

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sentry.Sentry
import io.sentry.UserFeedback
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SettingsFeedbackViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsFeedbackUiState())
    val uiState = _uiState.asStateFlow()

    fun sendFeedback(
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        if (_uiState.value.messageTitle.isNotEmpty()) {
            val sentryId = Sentry.captureMessage(_uiState.value.messageTitle)

            val userFeedback = UserFeedback(sentryId).apply {
                comments = _uiState.value.messageDescription
            }

            Sentry.captureUserFeedback(userFeedback)

            onSuccess.invoke()
        } else {
            onFailure.invoke("Please provide a title")
        }
    }

    fun updateMessageTitle(value: String) {
        _uiState.update {
            it.copy(
                messageTitle = value
            )
        }
    }

    fun updateMessageDescription(value: String) {
        _uiState.update {
            it.copy(
                messageDescription = value
            )
        }
    }
}