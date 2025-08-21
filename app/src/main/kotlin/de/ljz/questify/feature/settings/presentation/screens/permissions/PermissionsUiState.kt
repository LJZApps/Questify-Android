package de.ljz.questify.feature.settings.presentation.screens.permissions

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Definiert den Zustand der Permissions-UI.
 *
 * @param permissionItems Die Liste der Berechtigungen, die auf dem Bildschirm angezeigt werden.
 */
data class PermissionsUiState(
    val permissionItems: List<PermissionItem> = emptyList()
)

/**
 * Datenmodell für ein einzelnes Berechtigungs-Item in der UI.
 *
 * @param icon Das Icon, das die Berechtigung repräsentiert.
 * @param titleResId Die String-Ressourcen-ID für den Titel.
 * @param descriptionResId Die String-Ressourcen-ID für die Beschreibung.
 * @param isGranted Gibt an, ob die Berechtigung erteilt wurde.
 * @param requestAction Die Aktion, die beim Klick auf den Button ausgeführt wird.
 */
data class PermissionItem(
    val icon: ImageVector,
    val titleResId: Int,
    val descriptionResId: Int,
    val isGranted: Boolean,
    val requestAction: () -> Unit
)
