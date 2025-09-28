package de.ljz.questify.feature.quests.domain.use_cases

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import de.ljz.questify.core.receiver.QuestNotificationReceiver
import javax.inject.Inject

class CancelQuestNotificationsUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getNotificationsByQuestIdUseCase: GetNotificationsByQuestIdUseCase,
    private val removeNotificationsUseCase: RemoveNotificationsUseCase
) {
    suspend operator fun invoke(id: Int) {
        val notifications = getNotificationsByQuestIdUseCase.invoke(id)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        notifications.forEach { notificationEntity ->
            val intent = Intent(context, QuestNotificationReceiver::class.java)

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                notificationEntity.id,
                intent,
                PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            if (pendingIntent != null) {
                alarmManager.cancel(pendingIntent)
            }
        }

        removeNotificationsUseCase.invoke(id = id)
    }
}