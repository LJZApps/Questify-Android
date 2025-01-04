package de.ljz.questify.ui.features.main.components

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem<T : Any>(
    val title: String,
    val icon: ImageVector,
    /**
     * Disable/Enable only this feature
     */
    val featureEnabled: Boolean = true,
    val route: T
)
