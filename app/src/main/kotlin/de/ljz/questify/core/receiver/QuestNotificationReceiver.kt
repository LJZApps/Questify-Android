package de.ljz.questify.core.receiver

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import dagger.hilt.android.AndroidEntryPoint
import de.ljz.questify.R
import de.ljz.questify.core.application.TAG
import de.ljz.questify.core.main.ActivityMain
import de.ljz.questify.domain.repositories.AppSettingsRepository
import de.ljz.questify.domain.repositories.QuestNotificationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class QuestNotificationReceiver : BroadcastReceiver() {
    private val questsGroupId = "de.jz.QUEST_NOTIFICATION_GROUP"

    @Inject
    lateinit var questNotificationRepository: QuestNotificationRepository

    @Inject
    lateinit var appSettingsRepository: AppSettingsRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        val notificationId = intent.getIntExtra("notificationId", 0)
        val questId = intent.getIntExtra("questId", 0)
        val title = intent.getStringExtra("title") ?: "Zeit für deine Quest!"
        val description = intent.getStringExtra("description") ?: "Du hast eine Quest zu erledigen"
        var hasNotified: Boolean = false
        var remindAgainTime: Int = 5

        CoroutineScope(Dispatchers.IO).launch {
            hasNotified = questNotificationRepository.isNotified(notificationId)

            appSettingsRepository.getAppSettings().collectLatest { settings ->
               remindAgainTime = settings.reminderTime
            }
        }

        val intent = Intent(context, ActivityMain::class.java).apply {
            action = Intent.ACTION_VIEW
            data = "questify://quest_detail/${questId}".toUri()
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        // Abschließen
        val completeQuestIntent = Intent(context, CompleteQuestReceiver::class.java).apply {
            putExtra("notificationId", notificationId)
            putExtra("questId", questId)
        }
        val completeQuestPendingIntent = PendingIntent.getBroadcast(
            context, notificationId, completeQuestIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val completeQuestAction = NotificationCompat.Action.Builder(
            null,
            "Abschließen",
            completeQuestPendingIntent
        ).build()

        // Erneut erinnern
        val remindAgainIntent = Intent(context, RemindAgainReceiver::class.java).apply {
            putExtra("notificationId", notificationId)
            putExtra("questId", questId)
            putExtra("remindAgainTime", remindAgainTime)
        }
        val remindAgainPendingIntent = PendingIntent.getBroadcast(
            context, notificationId, remindAgainIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val remindAgainAction = NotificationCompat.Action.Builder(
            null,
            "Erneut erinnern",
            remindAgainPendingIntent
        ).build()

        saveNotification(context, title, description)

        val notification = NotificationCompat.Builder(context, "quests")
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setColor(ContextCompat.getColor(context, R.color.notification_color))
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setGroup(questsGroupId)
            .addAction(completeQuestAction)
            .addAction(remindAgainAction)
            .build()

        val inboxStyle = NotificationCompat.InboxStyle().setBigContentTitle("Level auf, während du deine neuen Quests erledigst!")

        val summaryNotification = NotificationCompat.Builder(context, "quests")
            .setSmallIcon(R.drawable.ic_stat_name)
            .setContentTitle("Neue Quests")
            .setContentText("Es ist Zeit, mehrere Quests zu erledigen.")
            .setStyle(inboxStyle)
            .setGroup(questsGroupId)
            .setGroupSummary(true)
            .setAutoCancel(true)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (!hasNotified) {
            notificationManager.notify(notificationId, notification)
            notificationManager.notify(0, summaryNotification)

            CoroutineScope(Dispatchers.IO).launch {
                questNotificationRepository.setNotificationAsNotified(notificationId)
            }
        }
    }

    private fun saveNotification(context: Context, title: String, description: String) {
        val sharedPreferences = context.getSharedPreferences("quest_notifications", Context.MODE_PRIVATE)
        val notifications = sharedPreferences.getStringSet("notifications", mutableSetOf()) ?: mutableSetOf()
        notifications.add("$title: $description")
        sharedPreferences.edit().putStringSet("notifications", notifications).apply()
    }
}

