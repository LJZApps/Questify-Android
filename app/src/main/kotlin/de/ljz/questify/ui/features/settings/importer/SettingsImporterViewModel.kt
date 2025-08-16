package de.ljz.questify.ui.features.settings.importer

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import de.ljz.questify.data.importer.GoogleTasksImporter
import de.ljz.questify.domain.repositories.quests.QuestRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsImporterViewModel @Inject constructor(
    private val questRepository: QuestRepository,
    private val application: Application
) : ViewModel() {
    private val googleTasksImporter = GoogleTasksImporter(application)

    fun getGoogleSignInClient(): GoogleSignInClient {
        return googleTasksImporter.getSignInClient()
    }

    fun importGoogleTasks(account: GoogleSignInAccount) {
        viewModelScope.launch {
            try {
                val quests = googleTasksImporter.fetchTasks(account)
                questRepository.up
            }
        }
    }
}