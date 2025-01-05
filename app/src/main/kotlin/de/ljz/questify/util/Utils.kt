package de.ljz.questify.util

import android.app.AlarmManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.provider.Settings
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.app.NotificationManagerCompat
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer

const val PREFS_NAME = "changelog_prefs"
const val PREF_VERSION_KEY = "last_version_shown"

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

fun getTrophyIconByName(iconName: String): ImageVector {
    return when(iconName) {
        else -> Icons.Filled.EmojiEvents
    }
}

fun getLastShownVersion(context: Context): Int? {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    return sharedPreferences.getInt(PREF_VERSION_KEY, 0)
}

fun saveCurrentVersion(context: Context, currentVersion: Int) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putInt(PREF_VERSION_KEY, currentVersion)
        apply()
    }
}