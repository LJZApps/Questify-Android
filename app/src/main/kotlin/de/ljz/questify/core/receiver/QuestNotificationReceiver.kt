package de.ljz.questify.core.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import de.ljz.questify.R

@AndroidEntryPoint
class QuestNotificationReceiver : BroadcastReceiver() {
    private val questsGroupId = "de.jz.QUEST_NOTIFICATION_GROUP"

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        val notificationId = intent.getIntExtra("notificationId", 0)
        val title = intent.getStringExtra("title") ?: "Zeit für deine Quest!"
        val description = intent.getStringExtra("description") ?: "Du hast eine Quest zu erledigen"
        val trophyEnabled = intent.getBooleanExtra("trophyEnabled", false)

        // Speichere die neue Benachrichtigung in SharedPreferences
        saveNotification(context, title, description)

        // Erstelle die individuelle Benachrichtigung
        val notification = NotificationCompat.Builder(context, "quests")
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setColor(ContextCompat.getColor(context, R.color.notification_color))
            .setAutoCancel(true)
            .setGroup(questsGroupId)
            .build()

        // Erstelle die Zusammenfassungsbenachrichtigung
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

        // Sende die individuelle Benachrichtigung
        notificationManager.notify(notificationId, notification)

        // Sende die Zusammenfassungsbenachrichtigung
        notificationManager.notify(0, summaryNotification)
    }

    // Speichere die aktuelle Benachrichtigung in SharedPreferences
    private fun saveNotification(context: Context, title: String, description: String) {
        val sharedPreferences = context.getSharedPreferences("quest_notifications", Context.MODE_PRIVATE)
        val notifications = sharedPreferences.getStringSet("notifications", mutableSetOf()) ?: mutableSetOf()
        notifications.add("$title: $description")
        sharedPreferences.edit().putStringSet("notifications", notifications).apply()
    }
}

