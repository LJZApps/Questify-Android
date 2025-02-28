package de.ljz.questify.util

import android.app.AlarmManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.provider.Settings
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AllInclusive
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.EmojiPeople
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Hiking
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.SportsGymnastics
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material.icons.filled.WorkspacePremium
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

/*
fun getTrophyIconByName(iconName: String): ImageVector {
    return if(getFilledIconByName(iconName) != null) {
        getFilledIconByName(iconName)?.let {
            it
        }
    } else {
        Icons.Default.Star
    }
}
*/


fun getAllFilledIcons(): List<Pair<String, ImageVector>> {
    return listOf(
        "EmojiEvents" to Icons.Filled.EmojiEvents,
        "MilitaryTech" to Icons.Filled.MilitaryTech,
        "Star" to Icons.Filled.Star,
        "Leaderboard" to Icons.Filled.Leaderboard,
        "WorkspacePremium" to Icons.Filled.WorkspacePremium,
        "Shield" to Icons.Filled.Shield,
        "Bolt" to Icons.Filled.Bolt,
        "LocalFireDepartment" to Icons.Filled.LocalFireDepartment,
        "ElectricBolt" to Icons.Filled.ElectricBolt,
        "RocketLaunch" to Icons.Filled.RocketLaunch,
        "MyLocation" to Icons.Filled.MyLocation,
        "Explore" to Icons.Filled.Explore,
        "Extension" to Icons.Filled.Extension,
        "Psychology" to Icons.Filled.Psychology,
        "CheckCircle" to Icons.Filled.CheckCircle,
        "Verified" to Icons.Filled.Verified,
        "DirectionsWalk" to Icons.Filled.DirectionsWalk,
        "DirectionsRun" to Icons.Filled.DirectionsRun,
        "DirectionsBike" to Icons.Filled.DirectionsBike,
        "FitnessCenter" to Icons.Filled.FitnessCenter,
        "SportsGymnastics" to Icons.Filled.SportsGymnastics,
        "School" to Icons.Filled.School,
        "MenuBook" to Icons.Filled.MenuBook,
        "Edit" to Icons.Filled.Edit,
        "Create" to Icons.Filled.Create,
        "Lightbulb" to Icons.Filled.Lightbulb,
        "TipsAndUpdates" to Icons.Filled.TipsAndUpdates,
        "Build" to Icons.Filled.Build,
        "Gavel" to Icons.Filled.Gavel,
        "Groups" to Icons.Filled.Groups,
        "VolunteerActivism" to Icons.Filled.VolunteerActivism,
        "EmojiPeople" to Icons.Filled.EmojiPeople,
        "History" to Icons.Filled.History,
        "AccessTime" to Icons.Filled.AccessTime,
        "Speed" to Icons.Filled.Speed,
        "Landscape" to Icons.Filled.Landscape,
        "Hiking" to Icons.Filled.Hiking,
        "Savings" to Icons.Filled.Savings,
        "VpnKey" to Icons.Filled.VpnKey,
        "Lock" to Icons.Filled.Lock,
        "LockOpen" to Icons.Filled.LockOpen,
        "VisibilityOff" to Icons.Filled.VisibilityOff,
        "Public" to Icons.Filled.Public,
        "Hub" to Icons.Filled.Hub,
        "AllInclusive" to Icons.Filled.AllInclusive,
    )
}

fun getFilledIconByName(iconName: String): ImageVector? {
    return try {
        Icons.Filled::class.members
            .firstOrNull { it.name.equals(iconName, ignoreCase = true) }
            ?.call(Icons.Filled) as? ImageVector
    } catch (e: Exception) {
        null
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