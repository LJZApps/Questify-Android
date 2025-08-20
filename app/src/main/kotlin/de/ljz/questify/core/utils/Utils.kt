package de.ljz.questify.core.utils

import android.app.AlarmManager
import android.content.Context
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.core.app.NotificationManagerCompat
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer

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

@Composable
fun isKeyboardVisible(): Boolean {
    val imeInsets = WindowInsets.ime.getBottom(LocalDensity.current)
    return imeInsets > 0
}