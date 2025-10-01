package de.ljz.questify.core.utils

import android.app.AlarmManager
import android.content.Context
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer
import kotlin.math.pow

fun isNotificationPermissionGranted(context: Context): Boolean {
    return NotificationManagerCompat.from(context).areNotificationsEnabled()
}

fun isAlarmPermissionGranted(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        (context.getSystemService(Context.ALARM_SERVICE) as AlarmManager).canScheduleExactAlarms()
    } else {
        true
    }
}

fun isOverlayPermissionGranted(context: Context): Boolean {
    return Settings.canDrawOverlays(context)
}

@OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
fun <T: Any> getSerializedRouteName(route: T): String {
    return route::class.serializer().descriptor.serialName
}

const val BASE_XP = 100.0
const val LEVEL_FACTOR = 1.15

fun calculateXpForNextLevel(level: Int): Int {
    return (BASE_XP * LEVEL_FACTOR.pow(level - 1)).toInt()
}