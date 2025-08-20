package de.ljz.questify.core.receiver

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import dagger.hilt.android.AndroidEntryPoint
import de.ljz.questify.R
import de.ljz.questify.feature.quests.domain.repositories.QuestNotificationRepository
import de.ljz.questify.core.presentation.screens.ActivityMain
import de.ljz.questify.feature.remind_again.presentation.RemindAgainActivity
import de.ljz.questify.feature.settings.domain.repositories.AppSettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
        val title =
            intent.getStringExtra("title") ?: context.getString(R.string.quest_notification_title)
        val description = intent.getStringExtra("description")
            ?: context.getString(R.string.quest_notification_description)
        var hasNotified: Boolean = false

        CoroutineScope(Dispatchers.IO).launch {
            hasNotified = questNotificationRepository.isNotified(notificationId)
        }

        val intent = Intent(context, ActivityMain::class.java).apply {
            action = Intent.ACTION_VIEW
            data = "questify://quest_detail/${questId}".toUri()
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Complete quest
        val completeQuestIntent = Intent(context, CompleteQuestReceiver::class.java).apply {
            putExtra("notificationId", notificationId)
            putExtra("questId", questId)
        }
        val completeQuestPendingIntent = PendingIntent.getBroadcast(
            context,
            notificationId,
            completeQuestIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val completeQuestAction = NotificationCompat.Action.Builder(
            null,
            context.getString(R.string.quest_notification_action_complete),
            completeQuestPendingIntent
        ).build()

        // Remind again
        val remindAgainIntent = Intent(context, RemindAgainActivity::class.java).apply {
            putExtra("notificationId", notificationId)
            putExtra("questId", questId)
        }
        val remindAgainPendingIntent = PendingIntent.getActivity(
            context,
            notificationId,
            remindAgainIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val remindAgainAction = NotificationCompat.Action.Builder(
            null,
            context.getString(R.string.quest_notification_action_remind_again),
            remindAgainPendingIntent
        ).build()

        saveNotification(context, title, description)

        val notification = NotificationCompat.Builder(context, "quests")
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setGroup(questsGroupId)
            .addAction(completeQuestAction)
            .addAction(remindAgainAction)
            .build()
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (!hasNotified) {
            notificationManager.notify(notificationId, notification)

            CoroutineScope(Dispatchers.IO).launch {
                questNotificationRepository.setNotificationAsNotified(notificationId)
            }
        }
    }

    @SuppressLint("MutatingSharedPrefs")
    private fun saveNotification(context: Context, title: String, description: String) {
        val sharedPreferences =
            context.getSharedPreferences("quest_notifications", Context.MODE_PRIVATE)
        val notifications =
            sharedPreferences.getStringSet("notifications", mutableSetOf()) ?: mutableSetOf()
        notifications.add("$title: $description")
        sharedPreferences.edit().putStringSet("notifications", notifications).apply()
    }
}

