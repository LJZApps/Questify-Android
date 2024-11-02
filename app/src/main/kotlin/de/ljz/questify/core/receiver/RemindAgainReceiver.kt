package de.ljz.questify.core.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import de.ljz.questify.domain.models.notifications.QuestNotificationEntity
import de.ljz.questify.domain.repositories.AppSettingsRepository
import de.ljz.questify.domain.repositories.QuestNotificationRepository
import de.ljz.questify.domain.repositories.QuestRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
class RemindAgainReceiver : BroadcastReceiver() {

    @Inject
    lateinit var questRepository: QuestRepository

    @Inject
    lateinit var questNotificationRepository: QuestNotificationRepository

    @Inject
    lateinit var appSettingsRepository: AppSettingsRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        val questId = intent.getIntExtra("questId", -1)
        val notificationId = intent.getIntExtra("notificationId", 0)
        val remindAgainTime = intent.getIntExtra("remindAgainTime", 5)

        if (questId != -1) {
            CoroutineScope(Dispatchers.IO).launch {
                val triggerTime = System.currentTimeMillis() + remindAgainTime * 60 * 1000

                val questNotification = QuestNotificationEntity(
                    questId = questId.toInt(),
                    notifyAt = Date(triggerTime)
                )

                questNotificationRepository.addQuestNotification(questNotification)

                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        "Du wirst in $remindAgainTime Minuten erneut erinnert.",
                        Toast.LENGTH_LONG
                    ).show()
                }

                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.cancel(notificationId)
            }
        }
    }
}