package de.ljz.questify.core.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import de.ljz.questify.domain.repositories.QuestRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CompleteQuestReceiver : BroadcastReceiver() {

    @Inject
    lateinit var questRepository: QuestRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        val questId = intent.getIntExtra("questId", -1)
        val notificationId = intent.getIntExtra("notificationId", 0)

        if (questId != -1) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    questRepository.setQuestDone(questId, true)

                    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.cancel(notificationId)
                } catch (e: Exception) {
                    Log.e("CompleteQuestReceiver", "Error completing quest", e)
                }
            }
        }
    }
}