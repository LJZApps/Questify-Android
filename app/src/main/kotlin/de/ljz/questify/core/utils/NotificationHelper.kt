package de.ljz.questify.core.utils

import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext
import de.ljz.questify.R
import javax.inject.Inject

class NotificationHelper @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    fun createNotificationChannel() {
        val groupId = "quest_group"
        val groupName = context.getString(R.string.notification_channel_quests_title)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val questGroup = NotificationChannelGroup(groupId, groupName)
        notificationManager.createNotificationChannelGroup(questGroup)

        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build()

        val soundUri =
            ("android.resource://" + context.packageName + "/" + R.raw.quest_notification).toUri()

        val channel = NotificationChannel(
            "quests",
            context.getString(R.string.notification_channel_reminder_title),
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = context.getString(R.string.notification_channel_reminders_description)
            importance = NotificationManager.IMPORTANCE_HIGH
            group = groupId
            setSound(soundUri, audioAttributes)
        }

        notificationManager.createNotificationChannel(channel)
    }
}