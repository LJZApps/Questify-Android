package de.ljz.questify.core.worker

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import de.ljz.questify.core.application.Difficulty
import de.ljz.questify.core.receiver.QuestNotificationReceiver
import de.ljz.questify.domain.models.notifications.QuestNotificationEntity
import de.ljz.questify.domain.repositories.QuestNotificationRepository
import de.ljz.questify.domain.repositories.quests.QuestRepository
import kotlinx.coroutines.flow.collectLatest

@HiltWorker
class QuestNotificationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val questNotificationRepository: QuestNotificationRepository,
    private val questRepository: QuestRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val notifications = questNotificationRepository.getPendingNotifications()

        notifications.collectLatest { notificationsList ->
            notificationsList.forEach { notification ->
                val quest = questRepository.getQuestById(notification.questId)
                if (!notification.notified && !quest.done) scheduleNotification(context, notification)
            }
        }

        return Result.success()
    }

    private fun scheduleNotification(context: Context, notification: QuestNotificationEntity) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val quest = questRepository.getQuestById(notification.questId)

        val intent = Intent(context, QuestNotificationReceiver::class.java).apply {
            putExtra("notificationId", notification.id)
            putExtra("questId", quest.id)
            putExtra("title", "Zeit für eine Quest!")
            putExtra("description", quest.title)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notification.id,
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        if (pendingIntent != null) {
            if (notification.notifyAt.time != alarmManager.nextAlarmClock?.triggerTime) {
                alarmManager.cancel(pendingIntent)
            } else {
                return
            }
        }

        val newPendingIntent = PendingIntent.getBroadcast(
            context,
            notification.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    notification.notifyAt.time,
                    newPendingIntent
                )
            } else {
                Log.d("ALARM_CHECK", "Exact alarm scheduling not permitted on this device.")
            }
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                notification.notifyAt.time,
                newPendingIntent
            )
        }
    }

    fun getRandomNotificationText(difficulty: Difficulty): String {
        return when (difficulty) {
            Difficulty.EASY -> easyTexts.random()
            Difficulty.MEDIUM -> mediumTexts.random()
            Difficulty.HARD -> hardTexts.random()
            Difficulty.EPIC -> epicTexts.random()
        }
    }

    private val easyTexts = listOf(
        "Eine leichte Quest wartet!",
        "Los geht’s – einfache Aufgabe!",
        "Nächste kleine Quest ruft!",
        "Zeit für eine kurze Herausforderung!",
        "Bereit für eine einfache Quest?"
    )

    private val mediumTexts = listOf(
        "Mach dich bereit für eine Quest!",
        "Bereit für die nächste Mission?",
        "Zeit für eine neue Quest!",
        "Eine spannende Aufgabe wartet!",
        "Nächste Stufe der Quest erwartet dich!"
    )

    private val hardTexts = listOf(
        "Eine harte Quest erwartet dich!",
        "Bereit für eine Herausforderung?",
        "Anspruchsvolle Quest wartet!",
        "Bereit, deine Skills zu testen?",
        "Eine echte Herausforderung ruft!"
    )

    private val epicTexts = listOf(
        "Eine epische Quest ruft!",
        "Die ultimative Aufgabe wartet!",
        "Legendäres Abenteuer erwartet dich!",
        "Zeit für eine epische Quest!",
        "Bereit für den ultimativen Test?"
    )

}