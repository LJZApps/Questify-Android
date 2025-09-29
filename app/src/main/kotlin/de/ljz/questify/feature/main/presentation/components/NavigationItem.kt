package de.ljz.questify.feature.main.presentation.components

import androidx.compose.ui.graphics.painter.Painter

data class NavigationItem<T : Any>(
    val title: String,
    val icon: Painter,
    /**
     * Disable/Enable only this feature
     */
    val featureEnabled: Boolean = true,
    val badge: String? = null,
    val route: T
)
