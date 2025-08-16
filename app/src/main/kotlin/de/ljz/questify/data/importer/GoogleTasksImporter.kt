package de.ljz.questify.data.importer

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.tasks.Tasks
import de.ljz.questify.core.application.Difficulty
import de.ljz.questify.domain.models.quests.QuestEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class GoogleTasksImporter @Inject constructor(
    private val context: Context
) {
    private val httpTransport = NetHttpTransport()
    private val jsonFactory = GsonFactory.getDefaultInstance()
    private val tasksReadonlyScope = "https://www.googleapis.com/auth/tasks.readonly"

    fun getSignInClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestScopes(Scope(tasksReadonlyScope))
            .build()
        return GoogleSignIn.getClient(context, gso)
    }

    suspend fun fetchTasks(account: GoogleSignInAccount): Result<List<QuestEntity>> {
        return withContext(Dispatchers.IO) {
            try {
                val credential = GoogleAccountCredential.usingOAuth2(context, listOf(tasksReadonlyScope))
                credential.selectedAccount = account.account

                // Dieser Builder wird jetzt gefunden!
                val service = Tasks.Builder(httpTransport, jsonFactory, credential)
                    .setApplicationName("Questify") // Wichtig für die API-Nutzungsbedingungen
                    .build()

                val taskLists = service.tasklists().list().execute().items ?: emptyList()
                val allQuests = mutableListOf<QuestEntity>()

                for (taskList in taskLists) {
                    val tasks = service.tasks().list(taskList.id).setFields("items(title, notes, due)").execute().items ?: continue
                    for (task in tasks) {
                        if (task.title.isNullOrBlank()) continue
                        allQuests.add(
                            QuestEntity(
                                title = task.title,
                                notes = task.notes ?: "",
                                difficulty = Difficulty.NONE,
                                createdAt = (task.updated ?: Date()) as Date
                            )
                        )
                    }
                }
                Result.success(allQuests)
            } catch (e: Exception) {
                e.printStackTrace()
                Result.failure(e)
            }
        }
    }
}