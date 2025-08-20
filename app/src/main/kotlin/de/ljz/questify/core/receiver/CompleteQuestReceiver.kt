package de.ljz.questify.core.receiver

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import de.ljz.questify.core.domain.repositories.quest_notifications.QuestNotificationRepository
import de.ljz.questify.feature.quests.domain.repositories.QuestRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CompleteQuestReceiver : BroadcastReceiver() {

    @Inject
    lateinit var questRepository: QuestRepository


    @Inject
    lateinit var questNotificationRepository: QuestNotificationRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        val questId = intent.getIntExtra("questId", -1)
        val notificationId = intent.getIntExtra("notificationId", 0)

        if (questId != -1) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    questRepository.setQuestDone(questId, true)

                    val notifications =
                        questNotificationRepository.getNotificationsByQuestId(questId)
                    val alarmManager =
                        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

                    notifications.forEach { notification ->
                        val intent = Intent(context, QuestNotificationReceiver::class.java)

                        val pendingIntent = PendingIntent.getBroadcast(
                            context,
                            notification.id,
                            intent,
                            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                        )

                        if (pendingIntent != null) {
                            alarmManager.cancel(pendingIntent)
                        }
                    }

                    questNotificationRepository.removeNotifications(questId)

                    val notificationManager =
                        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.cancel(notificationId)
                } catch (e: Exception) {
                    Log.e("CompleteQuestReceiver", "Error completing quest", e)
                }
            }
        }
    }
}